//
//  CaptchaRequest.swift
//  captcha_swift
//
//  Created by kean_qi on 2020/4/30.
//  Copyright © 2020 kean_qi. All rights reserved.
//

import UIKit
import HandyJSON
import SwiftyJSON

class CaptchaRequest {
    //获取验证码接口
    class func captchaAccept(_ type: CaptchaType ,success:@escaping (CaptchaResponseData) ->(), failure:@escaping (Error) ->()) {
        let url = "/captcha/get"
        var s = "blockPuzzle";
        switch type {
        case CaptchaType.puzzle:
            s = "blockPuzzle";
        case CaptchaType.clickword:
            s = "clickWord"
        }
        let params = [
            "captchaType": s, //验证码类型
            "distinguishSignatureVerificationMethod":"ios" //网关校验处理 可自行配置
        ];
        AJBaseRequest.sharedInstance.baseRequest(url: url, reqData: params, success: { (response) in
            let res = JSON(response!)
            let s = CaptchaResponseData(originalImageBase64: res["originalImageBase64"].stringValue, jigsawImageBase64: res["jigsawImageBase64"].stringValue, token: res["token"].stringValue, result: res["result"].stringValue, wordList: res["wordList"].arrayObject ?? [])
                            success(s)

        }) { (error) in
            failure(error)
        }
    }


    //校验验证码
    class func captchaCheck(_ type: CaptchaType, pointJson: String = "", token: String = "",success:@escaping (CaptchaResponseData) ->(), failure:@escaping (Error) ->()) {
        let url = "/captcha/check"
        var s = "blockPuzzle";
        switch type {
        case CaptchaType.puzzle:
            s = "blockPuzzle";
        case CaptchaType.clickword:
            s = "clickWord"
        }
        let params = [
            "pointJson":pointJson, //加密后的坐标
            "captchaType": s, //验证码类型
            "token":token, //获取验证码得到的token
            "distinguishSignatureVerificationMethod":"ios"
        ]
        AJBaseRequest.sharedInstance.baseRequest(url: url, reqData: params, success: { (response) in
            let res = JSON(response!)
            let s = CaptchaResponseData(originalImageBase64: res["originalImageBase64"].stringValue, jigsawImageBase64: res["jigsawImageBase64"].stringValue, token: res["token"].stringValue, result: res["result"].stringValue)
                            success(s)
        }) { (error) in
            failure(error)
        }
    }
}

struct CaptchaResponseData {
    var originalImageBase64: String = ""
    var jigsawImageBase64: String = ""
    var token: String = ""
    var result: String = ""
    //点选文字
    var wordList: [Any] = []
    
}






