package com.cosmoplat.entity.front;

import com.cosmoplat.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/10/30
 * Description: No Description
 */
@Data
public class User extends BaseEntity implements Serializable {
    private Long id;
    private String username;
}
