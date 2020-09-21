/*   
 * Project: OSMP
 * FileName: SqlParserUtils.java
 * version: V1.0
 */
package com.osmp.jdbc.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.osmp.jdbc.parse.sqlblock.AdvancedBlock;
import com.osmp.jdbc.parse.sqlblock.ChooseBlock;
import com.osmp.jdbc.parse.sqlblock.IfOrWhenBlock;
import com.osmp.jdbc.parse.sqlblock.InBlock;
import com.osmp.jdbc.parse.sqlblock.IncludeBlock;
import com.osmp.jdbc.parse.sqlblock.OrderByBlock;
import com.osmp.jdbc.parse.sqlblock.OrdersBlock;
import com.osmp.jdbc.parse.sqlblock.OtherwiseBlock;
import com.osmp.jdbc.parse.sqlblock.SQLBlock;
import com.osmp.jdbc.parse.sqlblock.StringBlock;
import com.osmp.jdbc.parse.sqlblock.WhereBlock;

/**
 * sql文件解析工具
 * @author heyu
 *
 */
public class SqlParserUtils {
    private static final Logger logger = LoggerFactory.getLogger(SqlParserUtils.class);
    
    private static final String regex = "(==)|(!=)|(<)|(>)|(>=)|(<=)|(is null)|(not null)|(is empty)|(not empty)";
    
    /**
     * 解析sql xml文件为SqlStatement块，以便动态组装sql
     * @param file sql xml文件
     * @return
     */
    public static Map<String, SqlStatement> parse(File file){
        if(file == null || !file.exists()) return null;
        Map<String, SqlStatement> sqlMap = null;
        try {
            XmlParser xmlParser = XmlParser.createSqlXmlParser(file);
            Node sqlsNode = xmlParser.evalRootNode("/sqls");
            if(sqlsNode == null) return null;
            NodeList n_list = sqlsNode.getChildNodes();
            if(n_list == null || n_list.getLength() < 1) return null;
            sqlMap = new HashMap<String,SqlStatement>();
            int len = n_list.getLength();
            for(int i = 0;i< len;i++){
                Node n_node = n_list.item(i);
                if(n_node == null) continue;
                if(!("sql".equalsIgnoreCase(n_node.getNodeName()) || 
                   "select".equalsIgnoreCase(n_node.getNodeName()) || 
                   "update".equalsIgnoreCase(n_node.getNodeName()) ||
                   "insert".equalsIgnoreCase(n_node.getNodeName()) ||
                   "delete".equalsIgnoreCase(n_node.getNodeName()))) continue;
                String idAttr = n_node.getAttributes().getNamedItem("id").getNodeValue();
                List<SQLBlock> sqlblocks = new ArrayList<SQLBlock>();
                NodeList c_list = n_node.getChildNodes();
                if(c_list == null || c_list.getLength() < 1) continue;
                int c_len = c_list.getLength();
                for(int j = 0;j<c_len;j++){
                    Node c_node = c_list.item(j);
                    SQLBlock block = parseNode(c_node);
                    if(block != null){
                        sqlblocks.add(parseNode(c_node));
                    }
                }
                sqlMap.put(idAttr,new SqlStatement(idAttr,sqlblocks));
            }
        } catch (FileNotFoundException e) {
            logger.error("sql文件未找到",e);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } 
        return sqlMap;
    }
    
    /**
     * 解析节点
     * @param node
     * @return
     */
    private static SQLBlock parseNode(Node node){
        if(node == null) return null;
        String nodeName = node.getNodeName();
        String nodeValue = node.getTextContent().trim();
        if(node.getNodeType() == Node.CDATA_SECTION_NODE){
            CDATASection cdataNode = (CDATASection) node;
            String cdataNodeValue = cdataNode.getTextContent();
            if("".equals(cdataNodeValue.trim())) return null;
            return new StringBlock(cdataNodeValue);
        }else if(node.getNodeType() == Node.TEXT_NODE){
            if("".equals(nodeValue.trim())) return null;
            return new StringBlock(nodeValue);
        }else if("if".equals(nodeName)){
            if(node.getAttributes() == null || 
                    node.getAttributes().getNamedItem("test") == null) return null;
            IfOrWhenBlock block = createBlock(node);
            block.addSqlBlocks(parseNodeBlocks(node));
            return block;
        }else if("choose".equals(nodeName)){
            ChooseBlock block = new ChooseBlock();
            block.addSqlBlocks(parseNodeBlocks(node));
            return block;
        }else if("where".equals(nodeName)){
            WhereBlock block = new WhereBlock();
            block.addSqlBlocks(parseNodeBlocks(node));
            return block;
        }else if("orders".equals(nodeName)){
            OrdersBlock block = new OrdersBlock();
            block.addSqlBlocks(parseNodeBlocks(node));
            return block;
        }else if("include".equals(nodeName)){
            if(node.getAttributes() == null || node.getAttributes().getNamedItem("sql") == null) return null;
            String sqld = node.getAttributes().getNamedItem("sql").getNodeValue();
            IncludeBlock block = new IncludeBlock(sqld);
            return block;
        }
        return null;
    }
    /**
     * 递归解析子节点
     * @param node
     * @return
     */
    private static List<SQLBlock> parseNodeBlocks(Node node){
        NodeList n_list = node.getChildNodes();
        if(n_list == null || n_list.getLength() < 1) return null;
        List<SQLBlock> sqlBlocks = new ArrayList<SQLBlock>();
        int len = n_list.getLength();
        for(int j = 0;j<len;j++){
            Node c_node = n_list.item(j);
            if(c_node == null) continue;
            String nodeName = c_node.getNodeName();
            String nodeValue = c_node.getTextContent().trim();
            if(c_node.getNodeType() == Node.CDATA_SECTION_NODE){
                CDATASection cdataNode = (CDATASection) c_node;
                String cdataNodeValue = cdataNode.getTextContent();
                if("".equals(cdataNodeValue.trim())) continue;
                sqlBlocks.add(new StringBlock(cdataNodeValue));
            }else if(c_node.getNodeType() == Node.TEXT_NODE){
                if("".equals(nodeValue.trim())) continue;
                sqlBlocks.add(new StringBlock(nodeValue));
            }else if("if".equals(nodeName) || "when".equals(nodeName)){
                if(c_node.getAttributes() == null || 
                        c_node.getAttributes().getNamedItem("test") == null) continue;
                IfOrWhenBlock block = createBlock(c_node);
                block.addSqlBlocks(parseNodeBlocks(c_node));
                sqlBlocks.add(block);
            }else if("choose".equals(nodeName)){
                ChooseBlock block = new ChooseBlock();
                block.addSqlBlocks(parseNodeBlocks(c_node));
                sqlBlocks.add(block);
            }else if("otherwise".equals(nodeName)){
                OtherwiseBlock block = new OtherwiseBlock();
                block.addSqlBlocks(parseNodeBlocks(c_node));
                sqlBlocks.add(block);
            }else if("where".equals(nodeName)){
                WhereBlock block = new WhereBlock();
                block.addSqlBlocks(parseNodeBlocks(c_node));
                sqlBlocks.add(block);
            }else if("orders".equals(nodeName)){
                OrdersBlock block = new OrdersBlock();
                block.addSqlBlocks(parseNodeBlocks(c_node));
                sqlBlocks.add(block);
            }else if("orderby".equals(nodeName)){
                if(c_node.getAttributes() == null || 
                        c_node.getAttributes().getNamedItem("orderColumn") == null) continue;
                String orderColumn = c_node.getAttributes().getNamedItem("orderColumn").getNodeValue();
                if(orderColumn == null || "".equals(orderColumn)) continue;
                Node orderTypeVarNode = c_node.getAttributes().getNamedItem("orderTypeVar");
                Node orderTypeNode = c_node.getAttributes().getNamedItem("orderType");
                String orderTypeVar = "";
                String orderType = "";
                if(orderTypeVarNode != null) {
                    orderTypeVar = orderTypeVarNode.getNodeValue();
                }
                if(orderTypeNode != null) {
                    orderType = orderTypeNode.getNodeValue();
                }
                sqlBlocks.add(new OrderByBlock(orderColumn,orderTypeVar,orderType));
            }else if("advanced".equals(nodeName)){
                if(c_node.getAttributes() == null || 
                        c_node.getAttributes().getNamedItem("advancedProperty") == null || 
                        c_node.getAttributes().getNamedItem("separator") == null) continue;
                String advancedProperty = c_node.getAttributes().getNamedItem("advancedProperty").getNodeValue();
                String separator = c_node.getAttributes().getNamedItem("separator").getNodeValue();
                if(advancedProperty == null || separator == null 
                        || "".equals(advancedProperty.trim())
                        || "".equals(separator.trim())) continue;
                sqlBlocks.add(new AdvancedBlock(advancedProperty, separator));
                
            }else if("in".equals(nodeName)){
                if(c_node.getAttributes() == null || 
                  c_node.getAttributes().getNamedItem("inProperty") == null || 
                  c_node.getAttributes().getNamedItem("separator") == null) continue;
                String inProperty = c_node.getAttributes().getNamedItem("inProperty").getNodeValue();
                String separator = c_node.getAttributes().getNamedItem("separator").getNodeValue();
                if(inProperty == null || separator == null 
                        || "".equals(inProperty.trim())
                        || "".equals(separator.trim())) continue;
                InBlock block = new InBlock(inProperty, separator);
                block.addSqlBlocks(parseNodeBlocks(c_node));
                sqlBlocks.add(block);
            }else if("include".equals(nodeName)){
                if(c_node.getAttributes() == null || c_node.getAttributes().getNamedItem("sql") == null) continue;
                String sqld = c_node.getAttributes().getNamedItem("sql").getNodeValue();
                IncludeBlock block = new IncludeBlock(sqld);
                sqlBlocks.add(block);
            }
        }
        return sqlBlocks;
    }
    
    /**
     * 创建if或者when条件块
     * @param node
     * @return
     */
    private static IfOrWhenBlock createBlock(Node node){
        if(node == null) return null;
        String condStr = node.getAttributes().getNamedItem("test").getNodeValue().trim();
        if(condStr == null) return null;
        String[] pAndV = condStr.split(regex);
        String condExpresstion = condStr;
        for(String dt : pAndV){
            condExpresstion = condExpresstion.replaceFirst(dt, "").trim();
        }
        if(condExpresstion == null || "".equals(condExpresstion)){
            return new IfOrWhenBlock( pAndV[0].trim(), Condition.EQUAL, "true");
        }
        
        String val = null;
        
        //条件不包含判断逻辑，默认判断其是否为true
        if(pAndV.length > 1){
            val =pAndV[1].trim();
        }
        return new IfOrWhenBlock(pAndV[0].trim(), Condition.getCondition(condExpresstion), val);
    }
    
}
