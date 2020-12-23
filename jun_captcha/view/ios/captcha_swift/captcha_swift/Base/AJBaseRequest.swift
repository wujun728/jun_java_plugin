//
//  AJBaseRequest.swift
//  crm
//
//  Created by kean_qi on 2019/12/23.
//  Copyright © 2019 kean_qi. All rights reserved.
//

import UIKit
import Foundation
import Alamofire
import SwiftyJSON
import HandyJSON

enum MethodType{
    case get
    case post
}
enum RequestResult{
    case ok(message: String) // code == 000000 请求成功
    case failed(message: String) //code > 000000 请求失败
    case error(error: Error) //请求出错
}
private let shareInstance = AJBaseRequest()
private var timeoutInterval: TimeInterval = 60  //请求超时时间
//IP地址
let kServerBaseUrl = "https://mirror.anji-plus.com/captcha-api"
//let kServerBaseUrl = "http://10.108.11.46:8080/api"
//let kServerBaseUrl = "http://10.108.12.20:8080/api"
//let kServerBaseUrl = "http://10.108.12.20:8086/"


class AJBaseRequest {
    class var sharedInstance: AJBaseRequest {
        return shareInstance
    }
}


extension AJBaseRequest {
    static let aManager: Alamofire.SessionManager = {
        let configuration = URLSessionConfiguration.default
        configuration.timeoutIntervalForRequest = 20
        return Alamofire.SessionManager(configuration: configuration)
    }()
    
    func baseRequest(url: String, method: MethodType = .post, reqData: [String : Any], autoShowMessage: Bool = true, success: @escaping (Any?) -> (), failure: @escaping (Error) -> ()){
//        let token = ""
//        let timeInterval = NSDate().timeIntervalSince1970 * 1000
        
//        let time = Int64(timeInterval)
//        let sign = self.getMD5Sign(reqData, time: String(time), token: token)
//        let params: [String : Any] = [
//            "time":time,
//            "token":token,
//            "reqData":reqData,
//            "sign":sign,
//        ]
        let params: [String : Any] = reqData
        AJBaseRequest.aManager.delegate.sessionDidReceiveChallenge = CertificateTrust.alamofireCertificateTrust
        let headers = ["Content-Type":"application/json"]
        let _method = method == MethodType.post ? HTTPMethod.post : HTTPMethod.get;
        let reqUrlStr = "\(kServerBaseUrl)\(url)"
        printLog(reqUrlStr)
        printLog(params)
        AJBaseRequest.aManager.request(reqUrlStr, method: _method, parameters: params.compactMapValues({ $0 }), encoding: JSONEncoding.default, headers: headers).responseJSON { (response) in
            switch response.result {
            case .success:
                if let value = response.result.value {
                    let json = JSON(value)
                    printLog(json)
                    let repCode = json["repCode"].stringValue
                    let repMsg = json["repMsg"].stringValue
                    guard repCode == "0000" else {
                        printLog("请求出错: \(repMsg)")
                        let err = NSError(domain: "\(repMsg)", code: 0, userInfo: nil)
                        failure(err)
                        return
                    }
                    success(json["repData"])
                }
            case .failure(let error):
                failure(error)
            }
        }
    }
}

extension AJBaseRequest{
    //MARK: MD5 sign 处理
    private func getMD5Sign(_ reqDataDict: [String: Any]? = nil, front: Int = 2, time:String, token: String) -> String {
        var reqDataStr:String = ""
        if let paramsDict = reqDataDict {
            reqDataStr = self.dicToJson(paramsDict).replacingOccurrences(of: "\\", with: "")
        }
        let valueStr = "reqData" + reqDataStr + "time" + time + "token" + token
        printLog(valueStr)
        return valueStr.md5().uppercased()
    }
    
    // MARK: - 字典转json
    private func dicToJson(_ dic: Dictionary<String, Any>) -> String {
        let dict : NSDictionary = dic as NSDictionary
        
        let jsonData = try? JSONSerialization.data(withJSONObject: dict, options: [])
        var jsonString = NSString()
        if (jsonData != nil) {
            jsonString = NSString(data: jsonData!, encoding: String.Encoding.utf8.rawValue)!
        }
        
        return jsonString as String
    }
}


/// prin输出
/// - Parameters:
///   - message: 输出内容
///   - logError: 是否错误 default is false
///   - file: 输出文件位置
///   - method: 对应方法
///   - line: 所在行
func printLog<T>(_ message: T,
                 _ logError: Bool = false,
                 file: String = #file,
                 method: String = #function,
                 line: Int = #line)
{
    if logError {
        print("\((file as NSString).lastPathComponent)\(method) [Line \(line)] : \(message)")
    } else {
        #if DEBUG
            print("\((file as NSString).lastPathComponent)\(method) [Line \(line)] : \(message)")
        #endif
    }
}




