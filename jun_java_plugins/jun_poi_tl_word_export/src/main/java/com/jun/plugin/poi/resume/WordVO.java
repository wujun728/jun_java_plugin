package com.jun.plugin.poi.resume;

import com.deepoove.poi.data.PictureRenderData;
import lombok.Data;

import java.util.List;

@Data
public class WordVO {
    private List<PictureRenderData> picture;

    private String problem;

    private String reason;
}
