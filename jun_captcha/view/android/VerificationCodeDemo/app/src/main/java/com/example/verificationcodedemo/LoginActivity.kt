package com.example.verificationcodedemo

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.verificationcodedemo.widget.BlockPuzzleDialog
import com.example.verificationcodedemo.widget.WordCaptchaDialog
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Date:2020/5/6
 * author:wuyan
 */
class LoginActivity : BaseActivity() {

    private val blockPuzzleDialog: BlockPuzzleDialog by lazy {
        BlockPuzzleDialog(this@LoginActivity)
    }
    private val wordCaptchaDialog: WordCaptchaDialog by lazy {
        WordCaptchaDialog(this@LoginActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bGo.setOnClickListener {
            blockPuzzleDialog.show()
        }

        bGo_word.setOnClickListener {
            wordCaptchaDialog.show()
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        //非默认值
        if (newConfig.fontScale.toInt() != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        var res = super.getResources()

        //非默认值
        if (res.getConfiguration().fontScale.toInt() != 1) {
            val newConfig = Configuration()
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

}
