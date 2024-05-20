package com.jun.plugin.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Slf4j
public class BeanCopyUtil {
	
//	 public static void main(String[] args) {
//		 cn.hutool.core.util.IdUtil
//	        System.out.println(IdUtil.fastUUID()); // 813bd123-69e9-42ae-97c4-2da7895235df
//	        System.out.println(IdUtil.fastSimpleUUID()); // 5547f7f9f0d2428787cdba7b6bd61616
//	        System.out.println(IdUtil.nanoId()); // y-E4JNXw_jURG0GE2sPV0
//	        System.out.println(IdUtil.nanoId(10)); // z6xBGVGM-L
//	        System.out.println(IdUtil.objectId()); // 6400442cdebadda465d32661
//	        System.out.println(IdUtil.getSnowflake(1, 1).nextIdStr()); // 1631180428218077184
//	    }

    /**
     * 拷贝同名属性，拷贝从左到右
     *
     * @param vo
     * @param dto
     */
    public static void VO2DTO(Object vo, Object dto) {
        if (vo == null) return;
        BeanCopier copier = BeanCopier.create(vo.getClass(), dto.getClass(), false);
        copier.copy(vo, dto, null);
    }

    /**
     * 复制并转换枚举
     * @param vo
     * @param dto
     */
    public static void VO2DTOAndCoverEnums(Object vo, Object dto) {
        VO2DTO(vo,dto);
        covertEnumsField(vo,dto);
    }
    /**
     * 将src对象中的枚举类型字典，转换为code,并设置到tar对象中
     * @param src
     * @param tar
     */
    public static void covertEnumsField(Object src, Object tar) {
        if(src == null) return;
        Arrays.stream(src.getClass().getDeclaredFields()).forEach(t -> {
            t.setAccessible(true);
            try {
                Object o = t.get(src);
                if (o != null && o.getClass().isEnum()) {
//                    System.out.println(t.getName() + ":" + o.toString());
                    Field tarField = tar.getClass().getDeclaredField(t.getName());
                    tarField.setAccessible(true);
                    tarField.set(tar,o.getClass().getMethod("getCode").invoke(o));
                }
            } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
                log.error("枚举转换异常：{}",e);
            }
        });

    }

    /**
     * 拷贝同名属性，拷贝从左到右
     * 适用：List.stream().map(t -> {XXXX}).collect(Collectors.toList());
     * 适用：List.foreach(item->{XXXX});
     * 适用：{
     * BusiInstDTO dto = new BusiInstDTO();
     * BeanCopyUtil.VO2DTO(t, dto);
     * return dto;
     * }
     *
     * @param vo
     * @param dto
     */
    public static Object voCopyNewDTO(Object vo, Object dto) {
        BeanCopier copier = BeanCopier.create(vo.getClass(), dto.getClass(), false);
        copier.copy(vo, dto, null);
        return dto;
    }

    /**
     * 拷贝同名属性，拷贝从左到右
     *
     * @param dto
     * @param entity
     */
    public static void DTO2Entity(Object dto, Object entity) {
        if (dto == null) return;
        BeanCopier copier = BeanCopier.create(dto.getClass(), entity.getClass(), false);
        copier.copy(dto, entity, null);
    }

    /**
     * 拷贝同名属性，拷贝从左到右
     *
     * @param source
     * @param target
     */
    public static void copyField(Object source, Object target) {
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }

//    /**
//     * 拷贝同名属性，拷贝从左到右
//     * @param source
//     * @param target
//     */
//    public static void copyField(Class source, Class target) {
//        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
//        copier.copy(source, target, null);
//    }
//    /**
//     * 拷贝同名属性，拷贝从左到右
//     * @param source
//     * @param target
//     */
//    public static void copyField(Class source, Class target, Converter converter) {
//        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), true);
//        copier.copy(source,target, converter);
//    }
}
