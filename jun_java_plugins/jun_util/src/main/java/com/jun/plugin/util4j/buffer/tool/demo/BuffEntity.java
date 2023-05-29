package com.jun.plugin.util4j.buffer.tool.demo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.jun.plugin.util4j.buffer.ByteBuffer;

public class BuffEntity implements Dto{

	private int id;
	private String name;
	private Date time;
	private Integer age;
	private int[] array;
	private List<int[]> listArray;
	private Set<Integer[]> setArray;
	private Queue<Byte[]> queues;
	private Map<List<int[]>,Set<Integer>> map;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public int[] getArray() {
		return array;
	}
	public void setArray(int[] array) {
		this.array = array;
	}
	public List<int[]> getListArray() {
		return listArray;
	}
	public void setListArray(List<int[]> listArray) {
		this.listArray = listArray;
	}
	public Set<Integer[]> getSetArray() {
		return setArray;
	}
	public void setSetArray(Set<Integer[]> setArray) {
		this.setArray = setArray;
	}
	public Queue<Byte[]> getQueues() {
		return queues;
	}
	public void setQueues(Queue<Byte[]> queues) {
		this.queues = queues;
	}
	public Map<List<int[]>, Set<Integer>> getMap() {
		return map;
	}
	public void setMap(Map<List<int[]>, Set<Integer>> map) {
		this.map = map;
	}
	
	//auto write begin
	@Override
	public void writeTo(ByteBuffer buffer) {
	//field->id
	int id=0;
	id=getId();
	buffer.writeInt(id);
	//field->name
	String name=null;
	name=getName();
	if (name!=null){
	buffer.writeBoolean(true);
	buffer.writeUTF(name);
	}else{	buffer.writeBoolean(false);}
	//field->time
	Date time=null;
	time=getTime();
	if (time!=null){
	buffer.writeBoolean(true);
	buffer.writeLong(time.getTime());
	}else{	buffer.writeBoolean(false);}
	//field->age
	Integer age=null;
	age=getAge();
	if (age!=null){
	buffer.writeBoolean(true);
	buffer.writeInt(age);
	}else{	buffer.writeBoolean(false);}
	//field->array
	int[] array=null;
	array=getArray();
	if (array!=null){
	buffer.writeBoolean(true);
	int array_l=array.length;
	buffer.writeInt(array_l);
	for(int array_i=0;array_i<array_l;array_i++){
	int array_d=array[array_i];
	buffer.writeInt(array_d);
	}
	}else{	buffer.writeBoolean(false);}
	//field->listArray
	List<int[]> listArray=null;
	listArray=getListArray();
	if (listArray!=null){
	buffer.writeBoolean(true);
	int listArray_ls=listArray.size();
	buffer.writeInt(listArray_ls);
	for(int[] listArray_lv:listArray){
	int listArray_lv_l=listArray_lv.length;
	buffer.writeInt(listArray_lv_l);
	for(int listArray_lv_i=0;listArray_lv_i<listArray_lv_l;listArray_lv_i++){
	int listArray_lv_d=listArray_lv[listArray_lv_i];
	buffer.writeInt(listArray_lv_d);
	}
	}
	}else{	buffer.writeBoolean(false);}
	//field->setArray
	Set<java.lang.Integer[]> setArray=null;
	setArray=getSetArray();
	if (setArray!=null){
	buffer.writeBoolean(true);
	int setArray_ls=setArray.size();
	buffer.writeInt(setArray_ls);
	for(Integer[] setArray_lv:setArray){
	int setArray_lv_l=setArray_lv.length;
	buffer.writeInt(setArray_lv_l);
	for(int setArray_lv_i=0;setArray_lv_i<setArray_lv_l;setArray_lv_i++){
	Integer setArray_lv_d=setArray_lv[setArray_lv_i];
	buffer.writeInt(setArray_lv_d);
	}
	}
	}else{	buffer.writeBoolean(false);}
	//field->queues
	Queue<java.lang.Byte[]> queues=null;
	queues=getQueues();
	if (queues!=null){
	buffer.writeBoolean(true);
	int queues_ls=queues.size();
	buffer.writeInt(queues_ls);
	for(Byte[] queues_lv:queues){
	int queues_lv_l=queues_lv.length;
	buffer.writeInt(queues_lv_l);
	for(int queues_lv_i=0;queues_lv_i<queues_lv_l;queues_lv_i++){
	Byte queues_lv_d=queues_lv[queues_lv_i];
	buffer.writeByte(queues_lv_d);
	}
	}
	}else{	buffer.writeBoolean(false);}
	//field->map
	Map<java.util.List<int[]>,java.util.Set<java.lang.Integer>> map=null;
	map=getMap();
	if (map!=null){
	buffer.writeBoolean(true);
	int map_ms=map.size();
	buffer.writeInt(map_ms);
	for(List<int[]> map_mk:map.keySet()){
	Set<java.lang.Integer> map_mv=map.get(map_mk);
	int map_mk_ls=map_mk.size();
	buffer.writeInt(map_mk_ls);
	for(int[] map_mk_lv:map_mk){
	int map_mk_lv_l=map_mk_lv.length;
	buffer.writeInt(map_mk_lv_l);
	for(int map_mk_lv_i=0;map_mk_lv_i<map_mk_lv_l;map_mk_lv_i++){
	int map_mk_lv_d=map_mk_lv[map_mk_lv_i];
	buffer.writeInt(map_mk_lv_d);
	}
	}
	int map_mv_ls=map_mv.size();
	buffer.writeInt(map_mv_ls);
	for(Integer map_mv_lv:map_mv){
	buffer.writeInt(map_mv_lv);
	}
	}
	}else{	buffer.writeBoolean(false);}
	}

	@Override
	public void readFrom(ByteBuffer buffer) {
	//field->id
	int id=0;
	id=buffer.readInt();
	setId(id);
	//field->name
	String name=null;
	if (buffer.readBoolean()){
	name=buffer.readUTF();
	}
	setName(name);
	//field->time
	Date time=null;
	if (buffer.readBoolean()){
	time=new Date();
	time.setTime(buffer.readLong());
	}
	setTime(time);
	//field->age
	Integer age=null;
	if (buffer.readBoolean()){
	age=buffer.readInt();
	}
	setAge(age);
	//field->array
	int[] array=null;
	if (buffer.readBoolean()){
	int array_l=buffer.readInt();
	array=(int[]) java.lang.reflect.Array.newInstance(int.class,array_l);
	for(int array_i=0;array_i<array_l;array_i++){
	int array_d;
	array_d=buffer.readInt();
	array[array_i]=array_d;
	}
	}
	setArray(array);
	//field->listArray
	List<int[]> listArray=null;
	if (buffer.readBoolean()){
	int listArray_ls=buffer.readInt();
	listArray=new java.util.ArrayList<>();
	for(int listArray_li=0;listArray_li<listArray_ls;listArray_li++){
	int[] listArray_lv;
	int listArray_lv_l=buffer.readInt();
	listArray_lv=(int[]) java.lang.reflect.Array.newInstance(int.class,listArray_lv_l);
	for(int listArray_lv_i=0;listArray_lv_i<listArray_lv_l;listArray_lv_i++){
	int listArray_lv_d;
	listArray_lv_d=buffer.readInt();
	listArray_lv[listArray_lv_i]=listArray_lv_d;
	}
	listArray.add(listArray_lv);
	}
	}
	setListArray(listArray);
	//field->setArray
	Set<java.lang.Integer[]> setArray=null;
	if (buffer.readBoolean()){
	int setArray_ls=buffer.readInt();
	setArray=new java.util.HashSet<>();
	for(int setArray_li=0;setArray_li<setArray_ls;setArray_li++){
	Integer[] setArray_lv;
	int setArray_lv_l=buffer.readInt();
	setArray_lv=(Integer[]) java.lang.reflect.Array.newInstance(Integer.class,setArray_lv_l);
	for(int setArray_lv_i=0;setArray_lv_i<setArray_lv_l;setArray_lv_i++){
	Integer setArray_lv_d;
	setArray_lv_d=buffer.readInt();
	setArray_lv[setArray_lv_i]=setArray_lv_d;
	}
	setArray.add(setArray_lv);
	}
	}
	setSetArray(setArray);
	//field->queues
	Queue<java.lang.Byte[]> queues=null;
	if (buffer.readBoolean()){
	int queues_ls=buffer.readInt();
	queues=new java.util.concurrent.ConcurrentLinkedQueue<>();
	for(int queues_li=0;queues_li<queues_ls;queues_li++){
	Byte[] queues_lv;
	int queues_lv_l=buffer.readInt();
	queues_lv=(Byte[]) java.lang.reflect.Array.newInstance(Byte.class,queues_lv_l);
	for(int queues_lv_i=0;queues_lv_i<queues_lv_l;queues_lv_i++){
	Byte queues_lv_d;
	queues_lv_d=buffer.readByte();
	queues_lv[queues_lv_i]=queues_lv_d;
	}
	queues.add(queues_lv);
	}
	}
	setQueues(queues);
	//field->map
	Map<java.util.List<int[]>,java.util.Set<java.lang.Integer>> map=null;
	if (buffer.readBoolean()){
	int map_ms=buffer.readInt();
	map=new java.util.HashMap<>();
	for(int map_mi=0;map_mi<map_ms;map_mi++){
	List<int[]> map_mk;
	Set<java.lang.Integer> map_mv;
	int map_mk_ls=buffer.readInt();
	map_mk=new java.util.ArrayList<>();
	for(int map_mk_li=0;map_mk_li<map_mk_ls;map_mk_li++){
	int[] map_mk_lv;
	int map_mk_lv_l=buffer.readInt();
	map_mk_lv=(int[]) java.lang.reflect.Array.newInstance(int.class,map_mk_lv_l);
	for(int map_mk_lv_i=0;map_mk_lv_i<map_mk_lv_l;map_mk_lv_i++){
	int map_mk_lv_d;
	map_mk_lv_d=buffer.readInt();
	map_mk_lv[map_mk_lv_i]=map_mk_lv_d;
	}
	map_mk.add(map_mk_lv);
	}
	int map_mv_ls=buffer.readInt();
	map_mv=new java.util.HashSet<>();
	for(int map_mv_li=0;map_mv_li<map_mv_ls;map_mv_li++){
	Integer map_mv_lv;
	map_mv_lv=buffer.readInt();
	map_mv.add(map_mv_lv);
	}
	map.put(map_mk,map_mv);
	}
	}
	setMap(map);
	}

//auto write end
}
