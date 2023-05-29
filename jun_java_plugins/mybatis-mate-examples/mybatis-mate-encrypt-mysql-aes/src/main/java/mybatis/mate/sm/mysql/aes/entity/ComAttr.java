/*
 * Copyright (c) 2021 os-parent Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mybatis.mate.sm.mysql.aes.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Tolerate;
import mybatis.mate.annotation.FieldEncrypt;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Clark
 * @date 2021-08-26
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@TableName("attr")
public class ComAttr extends Model<ComAttr> {

    private static final long serialVersionUID = 1L;
    @Tolerate
    public ComAttr() {}

    @TableId
    private String attrId;

    // 不指定 (encryptor = AesEncryptor.class) 会走全局处理器
    // @FieldEncrypt(encryptor = AesEncryptor.class)
    @FieldEncrypt
    private String attrTitle;

    @FieldEncrypt
    private String email;

    // 指定密钥
    @FieldEncrypt(password = "123456789")
    private String mobile;

    @Override
    public Serializable pkVal() {
        return this.attrId;
    }
}
