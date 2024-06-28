package io.github.wujun728.tree;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Dept {

    private Long id;

    private Integer pid;

    private String name;

}
