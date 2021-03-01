//
//  ESConfig.swift
//  captcha_swift
//
//  Created by kean_qi on 2020/4/30.
//  Copyright © 2020 kean_qi. All rights reserved.
//

import UIKit
import CryptoSwift
let key = "XwKsGlMcdPMEhR1B"

class ESConfig: NSObject {
    //加密
    class func aesEncrypt(_ req: String) -> String{
        printLog("aesEncrypt :   \(req)")
        var encryptedBase64 = ""
        do {
            //使用AES-128-ECB加密模式
            let aes = try AES(key: key.bytes, blockMode: ECB(), padding: .pkcs7)
            //开始加密
            let encrypted = try aes.encrypt(req.bytes)
            encryptedBase64 = encrypted.toBase64() ?? "" //将加密结果转成base64形式
            print("加密结果(base64)：\(encryptedBase64)")
        } catch { }
        
        return encryptedBase64;
    }
    
    class func aesDncrypt(_ encodeStr: String) -> String{
        var decryptStr = ""
        do {
            //使用AES-128-ECB加密模式
            let aes = try AES(key: key.bytes, blockMode: ECB(), padding: .pkcs7)
            //开始解密1（从加密后的字符数组解密）
            //开始解密2（从加密后的base64字符串解密）
            decryptStr = try encodeStr.decryptBase64ToString(cipher: aes)
            print("解密结果2：\(decryptStr)")
        } catch { }
        return decryptStr
    }
    
    
    
    
    
    //序列化
    class func jsonEncode(_ req: CaptchaRequestModel) -> String{
        var paramsStr = ""
        do {
            let params = req
            print("params -- \(params)")
            let d = try JSONEncoder().encode(params)
            paramsStr = String(data: d, encoding: .utf8)!
        }catch{}
        return paramsStr
    }
    
    //序列化
    class func jsonClickWordEncode(_ reqList: Any) -> String{
        var paramsStr = ""
        do {
            let newData = try JSONSerialization.data(withJSONObject: reqList, options: .fragmentsAllowed)
            paramsStr = String(data: newData as Data, encoding: .utf8)!
        }catch{}
        return paramsStr
    }
    
    
    //反序列化
    class func jsonDncode(_ using: String) -> CaptchaRequestModel{
        var capModel = CaptchaRequestModel(x: 0, y: 0)
        do {
            if let data = using.data(using: String.Encoding.utf8) {
                capModel = try JSONDecoder().decode(CaptchaRequestModel.self, from: data)
            }
        }catch{}
        return capModel
    }
    
    
    
    
}

struct CaptchaRequestModel: Codable {
    var x: CGFloat
    var y: CGFloat
}

