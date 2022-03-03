package org.springrain.frame.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleDocValuesField;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatDocValuesField;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.util.IK.dic.Dictionary;
import org.springrain.frame.util.IK.lucene.IKAnalyzer;

/**
 * lucene 工具类
 * 
 * @author caomei
 *
 */
public class LuceneUtils {

    private static final Logger logger = LoggerFactory.getLogger(LuceneUtils.class);

    private LuceneUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    // 分词器,使用IK的精确匹配
    private static Analyzer analyzer = new IKAnalyzer();
    // private static Analyzer analyzer = new SmartChineseAnalyzer();

    /**
     * 根据关键词查询某个实体类的索引
     * 
     * @param clazz
     * @param page
     * @param searchkeyword
     * @return
     * @throws Exception
     */
    public static List searchDocument(String rootdir, Class clazz, Page page, String searchkeyword) throws Exception {
        LuceneFinder luceneFinder = new LuceneFinder(searchkeyword);
        return searchDocument(rootdir, clazz, page, luceneFinder);
    }

    /**
     * 根据LuceneSearchClause查询关键字,可以添加where条件和数值类型排序,最常用的接口
     * 
     * @param rootdir
     * @param clazz
     * @param page
     * @param luceneFinder
     * @return
     * @throws Exception
     */
    public static <T> List<T> searchDocument(String rootdir, Class<T> clazz, Page page, LuceneFinder luceneFinder)
            throws Exception {

        if (clazz == null || luceneFinder == null) {
            return null;
        }
        Query query = getBuilderByLuceneFinder(clazz, luceneFinder).build();
        return searchDocument(rootdir, clazz, page, query, luceneFinder.getSort());
    }

    /**
     * 根据entityId 查询一个对象
     * 
     * @param rootdir
     * @param clazz
     * @param value
     * @return
     * @throws Exception
     */
    public static <T> T searchDocumentById(String rootdir, Class<T> clazz, String value) throws Exception {

        if (clazz == null || StringUtils.isBlank(value)) {
            return null;
        }

        EntityInfo info = ClassUtils.getEntityInfoByClass(clazz);
        if (info == null) {
            return null;
        }

        Page page = new Page(1);
        page.setPageSize(1);

        LuceneFinder LuceneFinder = new LuceneFinder(null);
        LuceneFinder.addWhereCondition(info.getPkName(), info.getPkReturnType(), value);

        List<T> list = searchDocument(rootdir, clazz, page, LuceneFinder);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);

    }

    /**
     * 根据实体类保存到索引,结合 LuceneSearch和LuceneField注解
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    public static String saveDocument(String rootdir, Object entity) throws Exception {

        // 索引写入配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 获取索引目录文件
        Directory directory = getDirectory(rootdir, entity.getClass());
        if (directory == null) {
            return null;
        }
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        try {
            Document doc = bean2Document(entity);
            indexWriter.addDocument(doc);
            indexWriter.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            indexWriter.close();
            directory.close();
        }

        return null;
    }

    /**
     * 根据实体类批量保存到索引,结合 LuceneSearch和LuceneField注解
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    public static <T> String saveListDocument(String rootdir, List<T> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        // 索引写入配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Class clazz = list.get(0).getClass();
        // 获取索引目录文件
        Directory directory = getDirectory(rootdir, clazz);
        if (directory == null) {
            return null;
        }
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        try {
            List<Document> listDocument = new ArrayList<>();
            for (T t : list) {
                Document doc = bean2Document(t);
                listDocument.add(doc);
            }
            indexWriter.addDocuments(listDocument);
            indexWriter.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            indexWriter.close();
            directory.close();
        }

        return null;
    }

    /**
     * 根据Id删除索引
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    public static String deleteDocumentById(String rootdir, Class clazz, String id) throws Exception {
        List<FieldInfo> luceneFields = ClassUtils.getLuceneFields(clazz);
        if (CollectionUtils.isEmpty(luceneFields)) {
            return null;
        }

        String pkName = ClassUtils.getEntityInfoByClass(clazz).getPkName();
        // 索引写入配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 获取索引目录文件
        Directory directory = getDirectory(rootdir, clazz);
        if (directory == null) {
            return null;
        }
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 需要查询的关键字
        Term term = new Term(pkName, id.toString());
        TermQuery luceneQuery = new TermQuery(term);
        try {
            indexWriter.deleteDocuments(luceneQuery);
            indexWriter.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            indexWriter.close(); // 记得关闭,否则删除不会被同步到索引文件中
            directory.close(); // 关闭目录
        }

        return null;
    }

    /**
     * 批量删除对象的索引
     * 
     * @param rootdir
     * @param list
     * @return
     * @throws Exception
     */
    public static <T> String deleteListDocument(String rootdir, List<T> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Class clazz = list.get(0).getClass();
        List<String> ids = new ArrayList<>();

        for (T t : list) {
            Object pkValue_o = ClassUtils.getPKValue(t);
            if (pkValue_o == null) {
                continue;
            }
            String pkValue = pkValue_o.toString();
            ids.add(pkValue);
        }
        return deleteListDocument(rootdir, clazz, ids);
    }

    /**
     * 根据LuceneFinder,批量删除索引
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    public static String deleteListDocument(String rootdir, Class clazz, LuceneFinder luceneFinder) throws Exception {
        List<FieldInfo> luceneFields = ClassUtils.getLuceneFields(clazz);
        if (CollectionUtils.isEmpty(luceneFields)) {
            return null;
        }
        if (luceneFinder == null) {
            return null;
        }
        // 索引写入配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 获取索引目录文件
        Directory directory = getDirectory(rootdir, clazz);
        if (directory == null) {
            return null;
        }
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        try {
            Query query = getBuilderByLuceneFinder(clazz, luceneFinder).build();
            indexWriter.deleteDocuments(query);
            indexWriter.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            indexWriter.close(); // 记得关闭,否则删除不会被同步到索引文件中
            directory.close(); // 关闭目录
        }

        return null;
    }

    /**
     * 根据Id列表,批量删除索引
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    public static String deleteListDocument(String rootdir, Class clazz, List<String> ids) throws Exception {
        List<FieldInfo> luceneFields = ClassUtils.getLuceneFields(clazz);
        if (CollectionUtils.isEmpty(luceneFields)) {
            return null;
        }
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        String pkName = ClassUtils.getEntityInfoByClass(clazz).getPkName();
        // 索引写入配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 获取索引目录文件
        Directory directory = getDirectory(rootdir, clazz);
        if (directory == null) {
            return null;
        }
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        TermQuery[] listTermQuery = new TermQuery[ids.size()];

        for (int i = 0; i < ids.size(); i++) {
            // 需要查询的关键字
            Term term = new Term(pkName, ids.get(i));
            TermQuery luceneQuery = new TermQuery(term);
            listTermQuery[i] = luceneQuery;
        }
        try {
            indexWriter.deleteDocuments(listTermQuery);
            indexWriter.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            indexWriter.close(); // 记得关闭,否则删除不会被同步到索引文件中
            directory.close(); // 关闭目录
        }

        return null;
    }

    /**
     * 删除一个实体类的所有索引
     * 
     * @param ids
     * @param clazz
     * @return
     * @throws Exception
     */
    public static String deleteAllDocument(String rootdir, Class clazz) throws Exception {
        // 索引写入配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 获取索引目录文件
        Directory directory = getDirectory(rootdir, clazz);
        if (directory == null) {
            return null;
        }
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        try {
            indexWriter.deleteAll();
            indexWriter.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            indexWriter.close(); // 记得关闭,否则删除不会被同步到索引文件中
            directory.close(); // 关闭目录
        }
        return null;
    }

    /**
     * 修改一个对象的索引
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    public static String updateDocument(String rootdir, Object entity) throws Exception {

        String pkName = ClassUtils.getEntityInfoByClass(entity.getClass()).getPkName();
        Object pkValue_o = ClassUtils.getPKValue(entity);

        if (pkValue_o == null) {
            return null;
        }

        String pkValue = pkValue_o.toString();

        // 索引写入配置
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 获取索引目录文件
        Directory directory = getDirectory(rootdir, entity.getClass());
        if (directory == null) {
            return null;
        }
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        Term term = new Term(pkName, pkValue);

        try {
            Document doc = bean2Document(entity);
            indexWriter.updateDocument(term, doc);
            indexWriter.commit();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            indexWriter.close(); // 记得关闭,否则删除不会被同步到索引文件中
            directory.close(); // 关闭目录
        }

        return null;
    }

    /**
     * 批量修改索引
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    public static <T> String updateListDocument(String rootdir, List<T> list) throws Exception {

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        deleteListDocument(rootdir, list);
        saveListDocument(rootdir, list);
        return null;
    }

    /**
     * 封装获取查询的BooleanQuery.Builder
     * 
     * @param clazz
     * @param luceneFinder
     * @return
     * @throws Exception
     */
    private static Builder getBuilderByLuceneFinder(Class clazz, LuceneFinder luceneFinder) throws Exception {

        String[] fields = luceneFinder.getFields();
        Builder builder = new BooleanQuery.Builder();

        if (StringUtils.isNotBlank(luceneFinder.getKeyword())) {

            if (luceneFinder.getFields() == null) {
                List<FieldInfo> luceneTokenizedFields = ClassUtils.getLuceneTokenizedFields(clazz);
                List<String> fieldList = luceneFinder.getFieldList();
                for (FieldInfo finfo : luceneTokenizedFields) {
                    fieldList.add(finfo.getFieldName());
                }
                // luceneFinder.setFieldList(fieldList);
                fields = luceneFinder.getFields();
            }
            // 查询指定字段的转换器
            QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
            // 需要查询的关键字
            Query query = parser.parse(luceneFinder.getKeyword());

            builder.add(query, Occur.MUST);
        }

        List<BooleanClause> listClause = luceneFinder.getListCondition();

        if (CollectionUtils.isNotEmpty(listClause)) {
            for (BooleanClause bc : listClause) {
                builder.add(bc);
            }
        }

        return builder;

    }

    /**
     * 根据 Query 和 Sort查询数据列表,暂不对外开放.
     * 
     * @param rootdir
     * @param clazz
     * @param page
     * @param query
     * @param sort
     * @return
     * @throws Exception
     */
    private static <T> List<T> searchDocument(String rootdir, Class<T> clazz, Page page, Query query, Sort sort)
            throws Exception {
        // 获取索引目录文件
        Directory directory = getDirectory(rootdir, clazz);
        if (directory == null) {
            return null;
        }

        // 获取读取的索引
        IndexReader indexReader = DirectoryReader.open(directory);
        // 获取索引的查询器
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        List<T> list = null;
        try {
            TopDocs topDocs = null;
            int totalCount = indexSearcher.count(query);
            if (totalCount == 0) {
                return null;
            }

            ScoreDoc[] hits = null;

            if (page == null) {
                if (sort == null) {
                    topDocs = indexSearcher.search(query, totalCount);
                } else {
                    topDocs = indexSearcher.search(query, totalCount, sort);
                }

                hits = topDocs.scoreDocs;

            } else {
                // 总条数
                page.setTotalCount(totalCount);
                int start = page.getPageSize() * (page.getPageIndex() - 1);
                if (start >= totalCount) {
                    return null;
                }

                int end = page.getPageSize() * page.getPageIndex();

                if (end > totalCount) {
                    end = totalCount;
                }

                if (start >= end) {
                    return null;
                }
                if (sort == null) {
                    topDocs = indexSearcher.search(query, end);
                } else {
                    topDocs = indexSearcher.search(query, end, sort);
                }
                hits = new ScoreDoc[end - start];

                for (int i = start; i < end; i++) {
                    hits[i - start] = topDocs.scoreDocs[i];
                }

            }
            if (hits == null || hits.length < 1) {
                return null;
            }

            list = new ArrayList<>(hits.length);
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = indexSearcher.doc(hits[i].doc);
                // T t = clazz.newInstance();
                T t = document2Bean(hitDoc, clazz);
                list.add(t);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (indexReader != null) {
                indexReader.close();
            }
            if (directory != null) {
                directory.close();
            }

        }
        return list;
    }

    /**
     * 获取索引的目录
     * 
     * @param clazz
     * @return
     */
    public static File getIndexDirFile(String rootdir, Class clazz) {
        if (clazz == null) {
            return null;
        }
        File file = new File(rootdir + "/" + clazz.getName());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;

    }

    /**
     * 获取实体类的索引文档
     * 
     * @param clazz
     * @return
     * @throws IOException
     */
    public static Directory getDirectory(String rootdir, Class clazz) throws IOException {
        File indexDirFile = getIndexDirFile(rootdir, clazz);
        if (indexDirFile == null) {
            return null;
        }
        Path indexDirPath = indexDirFile.toPath();
        // 索引不可读
        if (!Files.isReadable(indexDirPath)) {
            return null;
        }
        // 获取索引目录文件
        Directory directory = FSDirectory.open(indexDirPath);
        return directory;

    }

    /**
     * 加载停止符
     * 
     * @param word
     */
    public static void addStopWord(String word) {
        Dictionary.addStopWord(word);
    }

    /**
     * 批量加载停止符
     * 
     * @param words
     *            Collection<String>词条列表
     */
    public static void addStopWord(Collection<String> words) {
        Dictionary.addStopWord(words);
    }

    /**
     * 加载一个量词
     * 
     * @param word
     */
    public static void addQuantifier(String word) {
        Dictionary.addQuantifier(word);
    }

    /**
     * 批量加载量词
     * 
     * @param words
     */
    public static void addQuantifier(Collection<String> words) {
        Dictionary.addQuantifier(words);
    }

    /**
     * 禁用词语
     * 
     * @param word
     */
    public static void disableDictWord(String word) {
        Dictionary.disableDictWord(word);
    }

    /**
     * 批量禁用词语
     * 
     * @param words
     */
    public static void disableDictWord(Collection<String> words) {
        Dictionary.disableDictWord(words);
    }

    /**
     * 加载新词语
     * 
     * @param word
     */

    public static void addDictWord(String word) {
        Dictionary.addDictWord(word);
    }

    /**
     * 批量加载新词条
     * 
     * @param words
     *            Collection<String>词条列表
     */
    public static void addDictWord(Collection<String> words) {
        Dictionary.addDictWord(words);
    }

    /**
     * 索引文档转化为bean
     * 
     * @param document
     * @param t
     * @return
     * @throws Exception
     */
    private static <T> T document2Bean(Document document, Class<T> clazz) throws Exception {

        if (document == null || clazz == null) {
            return null;
        }

        List<FieldInfo> luceneFields = ClassUtils.getLuceneFields(clazz);

        if (CollectionUtils.isEmpty(luceneFields)) {
            return null;
        }

        Map<String, Object> map = new HashedMap<>();

        for (FieldInfo finfo : luceneFields) {
            String fieldName = finfo.getFieldName();
            String fieldValue = document.get(fieldName);

            if (StringUtils.isBlank(fieldValue)) {
                continue;
            }

            Class fieldType = finfo.getFieldType();
            if (Integer.class == fieldType || int.class == fieldType) {// 数字

                // ClassUtils.setPropertieValue(fieldName, t, Integer.valueOf(fieldValue));
                map.put(fieldName, Integer.valueOf(fieldValue));

            } else if (BigInteger.class == fieldType) {// 数字
                // ClassUtils.setPropertieValue(fieldName, t, new BigInteger(fieldValue));
                map.put(fieldName, new BigInteger(fieldValue));

            } else if (Long.class == fieldType || long.class == fieldType) {// 数字
                // ClassUtils.setPropertieValue(fieldName, t, Long.valueOf(fieldValue));
                map.put(fieldName, Long.valueOf(fieldValue));

            } else if (Float.class == fieldType || float.class == fieldType) {// 数字
                // ClassUtils.setPropertieValue(fieldName, t, Float.valueOf(fieldValue));
                map.put(fieldName, Float.valueOf(fieldValue));

            } else if (Double.class == fieldType || double.class == fieldType) {// 数字
                // ClassUtils.setPropertieValue(fieldName, t, Double.valueOf(fieldValue));
                map.put(fieldName, Double.valueOf(fieldValue));
            } else if (BigDecimal.class == fieldType) {// BigDecimal
                // ClassUtils.setPropertieValue(fieldName, t, new BigDecimal(fieldValue));
                map.put(fieldName, new BigDecimal(fieldValue));
            } else if (Date.class == fieldType) {// 日期
                // ClassUtils.setPropertieValue(fieldName, t, new
                // Date(Long.valueOf(fieldValue)));
                map.put(fieldName, Long.valueOf(fieldValue));
            } else {
                // ClassUtils.setPropertieValue(fieldName, t, fieldValue);
                map.put(fieldName, fieldValue);
            }
        }

        T t = JsonUtils.readValue(JsonUtils.writeValueAsString(map), clazz);

        return t;

    }

    /**
     * bean转化为索引文档
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    private static Document bean2Document(Object entity) throws Exception {
        // 获取索引的字段,为null则不进行保存
        List<FieldInfo> luceneFields = ClassUtils.getLuceneFields(entity.getClass());
        if (CollectionUtils.isEmpty(luceneFields)) {
            return null;
        }

        Document doc = new Document();
        for (FieldInfo finfo : luceneFields) {

            String fieldName = finfo.getFieldName();
            Class fieldType = finfo.getFieldType();
            Object _obj = ClassUtils.getPropertieValue(fieldName, entity);
            if (_obj == null || StringUtils.isBlank(_obj.toString())) {
                continue;
            }
            String _value = _obj.toString();
            if (Integer.class == fieldType || int.class == fieldType) {// 数字进行存储和索引,不进行分词
                Integer value = Integer.valueOf(_value);

                if (finfo.getLuceneStored()) {
                    doc.add(new StoredField(fieldName, value));
                }

                if (finfo.getLuceneIndex()) {
                    doc.add(new IntPoint(fieldName, value));
                }

                if (finfo.getNumericSort()) {
                    doc.add(new NumericDocValuesField(fieldName, value));
                }

            } else if (Long.class == fieldType || long.class == fieldType) {// 数字进行存储和索引,不进行分词
                Long value = Long.valueOf(_value);

                if (finfo.getLuceneStored()) {
                    doc.add(new StoredField(fieldName, value));
                }

                if (finfo.getLuceneIndex()) {
                    doc.add(new LongPoint(fieldName, value));
                }
                if (finfo.getNumericSort()) {
                    doc.add(new NumericDocValuesField(fieldName, value));
                }
            } else if (Float.class == fieldType || float.class == fieldType) {// 数字进行存储和索引,不进行分词
                Float value = Float.valueOf(_value);

                if (finfo.getLuceneStored()) {
                    doc.add(new StoredField(fieldName, value));
                }

                if (finfo.getLuceneIndex()) {
                    doc.add(new FloatPoint(fieldName, value));
                }
                if (finfo.getNumericSort()) {
                    doc.add(new FloatDocValuesField(fieldName, value));
                }
            } else if (Double.class == fieldType || double.class == fieldType) {// 数字进行存储和索引,不进行分词
                Double value = Double.valueOf(_value);

                if (finfo.getLuceneStored()) {
                    doc.add(new StoredField(fieldName, value));
                }

                if (finfo.getLuceneIndex()) {
                    doc.add(new DoublePoint(fieldName, value));
                }
                if (finfo.getNumericSort()) {
                    doc.add(new DoubleDocValuesField(fieldName, value));
                }

            } else if (Date.class == fieldType) {// 数字进行存储和索引,不进行分词
                Long value = ((Date) _obj).getTime();
                if (finfo.getLuceneStored()) {
                    doc.add(new StoredField(fieldName, value));
                }

                if (finfo.getLuceneIndex()) {
                    doc.add(new LongPoint(fieldName, value));
                }
                if (finfo.getNumericSort()) {
                    doc.add(new NumericDocValuesField(fieldName, value));
                }
            } else if (BigInteger.class == fieldType) {// 数字
                if (finfo.getLuceneStored()) {
                    doc.add(new StringField(fieldName, _value, Store.YES));
                }
                if (finfo.getNumericSort()) {
                    doc.add(new NumericDocValuesField(fieldName, Long.valueOf(_value)));
                }
            } else if (BigDecimal.class == fieldType) {// 进行存储和索引,不进行分词引
                if (finfo.getLuceneStored()) {
                    doc.add(new StringField(fieldName, _value, Store.YES));
                }
            } else if (finfo.getPk()) {// 如果是主键,强制只保存和索引,不分词

                doc.add(new StringField(fieldName, _value, Store.YES));

            } else if (String.class == fieldType) {// 如果是字符串,一般是要进行分词的
                Store store = Store.YES;
                if (!finfo.getLuceneStored()) {
                    store = Store.NO;
                }

                // 暂未实现 facet
                // if(finfo.getLuceneFacet()){
                // doc.add(new FacetField(fieldName, _value));
                // }

                if (finfo.getStringTokenized()) {
                    doc.add(new TextField(fieldName, _value, store));
                } else if (finfo.getLuceneIndex()) {
                    doc.add(new StringField(fieldName, _value, store));
                } else {
                    doc.add(new StoredField(fieldName, _value));
                }
            } else {
                doc.add(new StoredField(fieldName, _value));
            }
            // _field = new Field(fieldName, _value, TextField.TYPE_STORED);

        }

        return doc;

    }

}