//
//  ViewController.swift
//  captcha_swift
//
//  Created by kean_qi on 2020/4/29.
//  Copyright © 2020 kean_qi. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }


    @IBAction func loginButtonClick(_ sender: UIButton) {
//        testDic()
        let type = CaptchaType(rawValue: sender.tag) ?? .puzzle
        CaptchaView.show(type) { (v) in
            print(v)
        }
    }
}
