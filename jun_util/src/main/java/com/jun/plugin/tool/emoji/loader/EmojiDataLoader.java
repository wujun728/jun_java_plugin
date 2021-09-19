package com.jun.plugin.tool.emoji.loader;

import java.util.List;

import com.jun.plugin.tool.emoji.model.Emoji;

/**
 * emoji loader
 *
 * @author xuxueli 2018-07-06 20:15:22
 */
public abstract class EmojiDataLoader {

    public abstract List<Emoji> loadEmojiData();

}
