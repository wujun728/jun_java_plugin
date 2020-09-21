/*   
 * Project: OSMP
 * FileName: Condition.java
 * version: V1.0
 */
package com.osmp.jdbc.parse;

public enum Condition {
    EQUAL("=="),NOTEQUAL("!="),GREAT(">"),LESS("<"),
    NOTLESS(">="),NOTGREAT("<="),
    ISNULL("is null"),NOTNULL("not null"),ISEMPTY("is empty"),NOTEMPTY("not empty");
    private String name;
    
    private Condition(String name) {
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public static Condition getCondition(String name){
        Condition[] cds = Condition.values();
        for(Condition cd : cds){
            if(cd.getName().equals(name)){
                return cd;
            }
        }
        return null;
    }
}
