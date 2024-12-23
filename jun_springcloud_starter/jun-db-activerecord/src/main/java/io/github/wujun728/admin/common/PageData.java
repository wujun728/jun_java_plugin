package io.github.wujun728.admin.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 * 分页数据
 * @param <T>
 */
@Data
public class PageData <T>{
    private List<T> items = new ArrayList<>();
    private int total;
}
