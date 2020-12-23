//
//  CertificateTrust.swift
//  Agencys
//
//  Created by Kean on 2020/4/16.
//  Copyright © 2018年 Kean. All rights reserved.
//

import Foundation
import Alamofire

class CertificateTrust {
    //定义host白名单
    static let selfSignedHosts = ["yue.haofenshu.com"]
    
    
    //自签名host认证
    class func selfSignedTrust(session: URLSession, challenge: URLAuthenticationChallenge) -> (URLSession.AuthChallengeDisposition, URLCredential?) {
        
        var disposition: URLSession.AuthChallengeDisposition = .performDefaultHandling
        var credential: URLCredential?
        
        if selfSignedHosts.contains(challenge.protectionSpace.host) {
            
            disposition = URLSession.AuthChallengeDisposition.useCredential
            credential = URLCredential(trust: challenge.protectionSpace.serverTrust!)
            
        }
        
        return (disposition, credential)
    }
    
    //双向认证
    class func serverTrust(session: URLSession, challenge: URLAuthenticationChallenge) -> (URLSession.AuthChallengeDisposition, URLCredential?) {
        
        var disposition: URLSession.AuthChallengeDisposition = .performDefaultHandling
        var credential: URLCredential?
        
        //grab remote certificate
        let serverTrust:SecTrust = challenge.protectionSpace.serverTrust!
        let certificate = SecTrustGetCertificateAtIndex(serverTrust, 0)!
        let remoteCertificateData = CFBridgingRetain(SecCertificateCopyData(certificate))!
        
        //grab local certificate
        let cerPath = Bundle.main.path(forResource: "haofenshu", ofType: "cer")!
        let cerUrl = URL(fileURLWithPath:cerPath)
        let localCertificateData = try! Data(contentsOf: cerUrl)
        
        if (remoteCertificateData.isEqual(localCertificateData) == true) {
            
            disposition = URLSession.AuthChallengeDisposition.useCredential
            credential = URLCredential(trust: serverTrust)
            
        } else {
            
            disposition = URLSession.AuthChallengeDisposition.cancelAuthenticationChallenge
            
        }
        
        return (disposition, credential)
    }
    
    
    //客户端认证
    class func clientTrust(session: URLSession, challenge: URLAuthenticationChallenge) -> (URLSession.AuthChallengeDisposition, URLCredential?) {
        
        let disposition = URLSession.AuthChallengeDisposition.useCredential
        var credential: URLCredential?
        
        //获取项目中P12证书文件的路径
        let path: String = Bundle.main.path(forResource: "haofenshu", ofType: "p12")!
        let PKCS12Data = NSData(contentsOfFile:path)!
        let key : NSString = kSecImportExportPassphrase as NSString
        let options : NSDictionary = [key : "123456"] //客户端证书密码
        
        var items: CFArray?
        
        let error = SecPKCS12Import(PKCS12Data, options, &items)
        
        if error == errSecSuccess {
            let cerItems: CFArray = items!
            
            if let itemArr = cerItems as Array?,
                let item = itemArr.first as? Dictionary<String, AnyObject> {
                
                // grab the identity
                let identityPointer = item["identity"];
                let secIdentityRef = identityPointer as! SecIdentity
                
                // grab the trust
                // let trustPointer = item["trust"]
                // let trustRef = trustPointer as! SecTrust
                
                // grab the cert
                let chainPointer = item["chain"]
                let chainRef = chainPointer as? [Any]
                
                // persistence: Credential should be stored only for this session
                credential = URLCredential.init(identity: secIdentityRef, certificates: chainRef, persistence: URLCredential.Persistence.forSession)
                
            }
            
        }
        
        return (disposition, credential)
    }
    
    
    //配置认证
    class func alamofireCertificateTrust(session: URLSession, challenge: URLAuthenticationChallenge) -> (URLSession.AuthChallengeDisposition, URLCredential?) {
        
        let method = challenge.protectionSpace.authenticationMethod
        
        //认证服务器证书
        //SecTrustRef validation required.  Applies to any protocol.
        if method == NSURLAuthenticationMethodServerTrust {
            
            //二选一
            //双向认证 (需要cer)
            //return self.serverTrust(session: session, challenge: challenge)
            
            //host认证 (这里不使用服务器证书认证，只需自定义的几个host即可信任)
            return self.selfSignedTrust(session: session, challenge: challenge)
            
        }
            
            //认证客户端证书
            //SSL Client certificate.  Applies to any protocol.
        else if method == NSURLAuthenticationMethodClientCertificate {
            
            return self.clientTrust(session: session, challenge: challenge);
            
        }
            
            // 其它情况（不接受认证）
        else {
            
            return (.cancelAuthenticationChallenge, nil)
            
        }
    }
    
    
}
