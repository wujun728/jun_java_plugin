     @Autowired
    private  SqlSessionFactory sqlSessionFactory; 
  
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<LinkedHashMap<String, String>> procedureExcute(LinkedHashMap<String, String> mapIn,LinkedHashMap<String, String> mapOut,
            StringBuffer callSql,int cursorIndex,StringBuffer proName) throws Exception{
        LOGGER.trace("@@@@@@@@@@@@@@@@@@@@@@@@Call "+proName+"--------------------- Start ");  
        Connection conn = getConnection();
        CallableStatement call = null;
        ResultSet rs = null;
        List<LinkedHashMap<String, String>> list = new ArrayList<>();
        try {
            call = conn.prepareCall(callSql.toString());
            int j=0;
            for(Map.Entry<String, String> entry:mapIn.entrySet()){ 
                if(entry.getKey().equals("String")){
                    call.setString(j+1, entry.getValue().toString());  
                }else if(entry.getKey().equals("Integer")){  
                    call.setInt(j+1, Integer.valueOf(entry.getValue().toString()));  
                }else if(entry.getKey().equals("Date")){ 
                    Date parseDate = GctDateUtil.parseDate(entry.getValue().toString());
                    java.sql.Date sqlDate=new java.sql.Date(parseDate.getTime());
                    call.setDate(j+1,sqlDate );  
                }else if(entry.getKey().equals("Double")){ 
                    call.setDouble(j+1, Double.parseDouble(entry.getValue().toString()));
                }else if(entry.getKey().equals("Long")){ 
                    call.setLong(j+1, Long.valueOf(entry.getValue().toString()));
                }else if(entry.getKey().equals("Float")){ 
                call.setFloat(j+1, Float.valueOf(entry.getValue().toString()));
                }
            }   
                call.registerOutParameter(cursorIndex,OracleTypes.CURSOR);
            call.execute();
            rs = (ResultSet) call.getObject(cursorIndex);
            
            if (rs == null) {
                return list;
            }

            int colunmCount = rs.getMetaData().getColumnCount();  
               ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {  
                LinkedHashMap  temp = new LinkedHashMap();  
                for (int i = 0; i < colunmCount; i++) {  
                    String columnName = metaData.getColumnName(i + 1);  
                    columnName = columnName.toUpperCase();  
                    temp.put(columnName, (rs.getString(i + 1)));  
                }  
                list.add(temp);
            }  
            LOGGER.trace("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Call "+proName.toString()+" -----------------END ");  
        } catch (Exception e) {
            LOGGER.error("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Call "+proName.toString()+"----------------- ³öÏÖÒì³£ ");  
            throw new Exception(e.getMessage(), e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new Exception(e.getMessage(), e);
                }
            }
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) {
                    throw new Exception(e.getMessage(), e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new Exception(e.getMessage(), e);
                }
            }
        }
        return list;
    }
    
    
       public Connection getConnection(){    
        Connection conn = null;    
        try {    
            conn =  sqlSessionFactory.getConfiguration().getEnvironment().getDataSource().getConnection();    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return conn;    
    }