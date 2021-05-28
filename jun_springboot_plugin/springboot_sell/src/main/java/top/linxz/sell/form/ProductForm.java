package top.linxz.sell.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductForm {
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;
}
