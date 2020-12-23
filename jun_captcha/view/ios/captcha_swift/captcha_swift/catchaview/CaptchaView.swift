//
//  CaptchaView.swift
//  captcha_swift
//
//  Created by kean_qi on 2020/4/29.
//  Copyright © 2020 kean_qi. All rights reserved.
//

import UIKit
/// 屏幕的宽
let SCREENW = UIScreen.main.bounds.size.width
/// 屏幕的高
let SCREENH = UIScreen.main.bounds.size.height

let topHeight:CGFloat = 40.0

let bottomHeight:CGFloat = 50.0



/// 校验模式
enum CaptchaType: Int {
    case puzzle     = 0 //"滑动拼图"
    case clickword   = 1 //"字符校验"
}

/// 状态模式
enum CaptchaResult{
    case normal
    case progress
    case success
    case failure
}

class CaptchaView: UIView {

    var completeBlock: ((String) -> Void)?
    var currentType = CaptchaType.puzzle
    //滑动拼图状态
    var currentPuzzleResultType = CaptchaResult.normal
    //字符校验状态
    var currentClickwordResultType = CaptchaResult.normal

    var repModel = CaptchaResponseData(){
        didSet {
            self.baseImageView.image = base64ConvertImage(repModel.originalImageBase64)
            self.puzzleImageView.image = base64ConvertImage(repModel.jigsawImageBase64)
        }
    }
    
    //底层ImageView
    var baseImageView = UIImageView()
    //拼图ImageView
    var puzzleImageView = UIImageView()
    //刷新按钮
    let refreshBtn      = UIButton()
    //点选验证码点选view列表
    var tapViewList: [CGPoint] = []
    
    
    
    //结果label
    var hintLabel = UILabel()
    //按钮尺寸
    let buttonSize = CGSize(width: 50.0, height: 50.0)
    
    //动画view
    let hintView      = UIView()
    //动画宽度
    let hintViewWidht = CGFloat(60)

    // ======== 通用视图相关 ========
    let contentView     = UIView() // 容器视图
    let shadowView      = UIView() // 背景视图
    
    
    //========puzzle============
    //滑块父view
    let sliderView = UIView()
    let sliderBgColor   = UIColor(red: 212/255, green: 212/255, blue: 212/255, alpha: 1.0)
    //滑动过程颜色view
    let progressView = UIView()
    //推动view
    let thumbView = UIView()
    let textLabel = UILabel()
    let promptLabel: UILabel = {
        let label = UILabel()
        label.text = "按住滑块拖动到最右边"
        label.textColor = UIColor.black.withAlphaComponent(0.6)
        label.font = UIFont.systemFont(ofSize: 15)
        label.textAlignment = .center
        return label
    }()
    let margin       = CGFloat(0.1) // 默认边距
    var puzzleThumbOffsetX: CGFloat {
        get {
            return margin
        }
    }
    //最后点击点
    var lastPointX = CGFloat.zero
    var offsetXList: Set<CGFloat> = [] // 滑动为匀速时,判定为机器操作,默认失败
    /// 背景图宽度
    var imageWidth: CGFloat {
        get { return self.baseImageView.width == 0 ? 310.0 : self.baseImageView.width }
    }
    /// 背景图高度
    var imageHeight: CGFloat {
        get {
            return self.baseImageView.height == 0 ? 155.0 : self.baseImageView.height
        }
    }
    
    lazy var resultView: UIView = {
        // 失败提示视图
        let view = UIView(frame: CGRect(x: 0, y: imageHeight, width: imageWidth, height: 20))
        let text = UILabel(frame: CGRect(x: 5, y: 0, width: imageWidth - 20, height: view.bounds.height))
        view.addSubview(text)
        view.backgroundColor = UIColor("d9534f").withAlphaComponent(0.4)
        let attrStr = NSMutableAttributedString(string: "验证失败: 再试一下吧~", attributes: [NSAttributedString.Key.foregroundColor:UIColor.black])
        attrStr.addAttributes([NSAttributedString.Key.foregroundColor:UIColor.red], range: NSRange(location: 0, length: 5))
        text.attributedText = attrStr
        text.font = UIFont.systemFont(ofSize: 12)
        self.baseImageView.insertSubview(view, at: 0)
        return view
    }()

    
    let topView: UIView = {
        let baseView = UIView(frame: CGRect(x: 0, y: 0, width: SCREENW*0.9, height: topHeight))
        let titleLabel = UILabel()
        titleLabel.text = "请完成安全验证"
        titleLabel.textColor = UIColor("8a8a8a")
        let closeButton = UIButton()
        closeButton.addTarget(self, action: #selector(close), for: .touchUpInside)
        titleLabel.frame = CGRect(x: 20, y: 5, width: 200, height: topHeight-1)
        closeButton.frame = CGRect(x: CGFloat(Double(baseView.frame.size.width - topHeight)), y: 0, width: topHeight-1, height: topHeight-1)
//        closeButton.setTitle("关闭", for: .normal)
        closeButton.setImage(UIImage(named: "close"), for: .normal)
//        closeButton.setTitleColor(.black, for: .normal)
        let lineVew = UIView()
        lineVew.frame = CGRect(x: 0, y: baseView.frame.maxY-1, width: baseView.frame.size.width, height: 1)
        lineVew.backgroundColor = UIColor("8a8a8a")
        baseView.addSubview(titleLabel)
        baseView.addSubview(closeButton)
        baseView.addSubview(lineVew)
        return baseView
    }()
    
    
    //mark:显示
    class func show(_ type: CaptchaType, completeBlock block: @escaping ((String) -> Void)){
        let view = CaptchaView(frame: UIScreen.main.bounds, type: type)
        view.completeBlock = block
        UIApplication.shared.windows[0].addSubview(view)
    }
    init(frame: CGRect, type: CaptchaType) {
        super.init(frame: frame)
        currentType = type
        _initView()
        setCaptchaType(type)
        
    }
    
    
    /// 请求接口
    func requestData(){
        if currentType == .clickword {
            self.setCurrentClickWordResultType(CaptchaResult.progress)
        }
        CaptchaRequest.captchaAccept(currentType, success: { (model) in
            self.repModel = model
            self.getRequestView(self.currentType)
        }) { (error) in
            self.repModel = CaptchaResponseData()
            self.getRequestView(self.currentType)
        }
        
    }
    /// 刷新初始化数据
    func getRequestView(_ type: CaptchaType){
        switch type {
        case .puzzle:
            self.sliderView.subviews.forEach {$0.removeFromSuperview()}
            self._initPuzzleFrame()
        case .clickword:
            print("字符校验码")
            self.baseImageView.reoveAllSubView()
            self.setClickWordImgView()
            self.setCurrentClickWordResultType(CaptchaResult.normal)
        }
    }
    
    /// 请求校验接口
    func requestCheckData(pointJson: String = ""){
        CaptchaRequest.captchaCheck(currentType, pointJson: pointJson, token: self.repModel.token, success: { (model) in
            self.showResult(true)
            
        }) { (error) in
            self.showResult(false)
        }
        
    }
    
    
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    /// 设置校验类型
    /// - Parameter type: 校验类型
    func setCaptchaType(_ type: CaptchaType){
        switch type {
        case .puzzle:
            initPuzzleView()
            requestData()
            
        case .clickword:
            initClickWordView()
            requestData()
        }
    }
    

    
    
    /// 关闭当前页面
    @objc func close() {
        if let block = completeBlock {
            block("")
        }
        removeFromSuperview()
    }

    // 刷新
    @objc func refresh() {
        self.requestData()
    }

    

    
    /// 校验结果
    func checkResult(_ point:CGPoint){
        switch currentType {
        case .puzzle:
            let pointEncode = ESConfig.jsonEncode(CaptchaRequestModel(x: point.x, y: 5))
            let pointJson = ESConfig.aesEncrypt(pointEncode)
            requestCheckData(pointJson: pointJson);
        case .clickword:
            
            var pointsList: [Any] = []
            for (_, item) in self.tapViewList.enumerated() {
                pointsList.append(["x": item.x, "y": item.y])
            }
            let pointEncode = ESConfig.jsonClickWordEncode(pointsList)
            let pointJson = ESConfig.aesEncrypt(pointEncode)
            requestCheckData(pointJson: pointJson);
        }
        
    }
    
    /// 显示结果页
    ///
    /// - Parameter isSuccess: 是否正确
    /// - note: 暂时只有错误时,才显示
    func showResult(_ isSuccess: Bool) {
        if let block = completeBlock {
            block("isSuccess\(isSuccess)")
        }
        switch currentType {
        case .puzzle:
            if isSuccess {
                self.setCurrentPuzzleResultType(CaptchaResult.success)
                t_delay(1) {
                    self.close()
                }
            } else {
                self.setCurrentPuzzleResultType(CaptchaResult.failure)
            
                UIView.animate(withDuration: 0.25, animations: {
                    self.resultView.transform = CGAffineTransform(translationX: 0, y: -20)
                }) { (finish) in
                    UIView.animate(withDuration: 0.5, delay: 0.75, options: UIView.AnimationOptions.allowUserInteraction, animations: {
                        self.resultView.transform = .identity

                    }, completion: nil)
                }
                
                UIView.animate(withDuration: 0.75, animations: {
                    self.thumbView.transform = .identity
                    self.puzzleImageView.transform = .identity
                    self.progressView.layer.frame = CGRect(x: 0, y: 0, width: self.thumbView.frame.midX, height: bottomHeight)
                }) { (finish) in
                    self.setCurrentPuzzleResultType(CaptchaResult.normal)
                    self.refresh()
                }
            }
        case .clickword:
            if isSuccess {
                self.setCurrentClickWordResultType(CaptchaResult.success)
                t_delay(1) {
                    self.close()
                }
                return
            } else {
                self.setCurrentClickWordResultType(CaptchaResult.failure)
                t_delay(1) {
                    self.refresh()
                }
                return
            }
            
        }
    }

}

extension CaptchaView {
    func _initView(){
        //底部背景view
        addSubview(shadowView)
        //容器view
        addSubview(contentView)
        //顶部导航view
        contentView.addSubview(topView)
        //图片view
        contentView.addSubview(baseImageView)
        //刷新按钮
        contentView.addSubview(refreshBtn)
        
        shadowView.frame = self.bounds
        contentView.frame = CGRect(x: 0, y: 0, width: UIScreen.main.bounds.size.width * 0.9, height: 310)
        contentView.center  = center
        
        baseImageView.backgroundColor = UIColor.lightGray.withAlphaComponent(0.2)
        baseImageView.clipsToBounds = true
        printLog("x: \((contentView.bounds.size.width - imageWidth)*0.5), y: \(topView.frame.maxY + 10), w: \(imageWidth), h: \(imageHeight),")
        baseImageView.frame = CGRect(x: (contentView.bounds.size.width - imageWidth)*0.5 , y: topView.frame.maxY + 10, width: imageWidth, height: imageHeight)

        refreshBtn.frame = CGRect(x: contentView.bounds.size.width - topHeight*2, y: topView.frame.maxY + 10, width: topHeight, height: topHeight)
        refreshBtn.setImage(UIImage(named: "refresh"), for: .normal)
        refreshBtn.addTarget(self, action: #selector(refresh), for: .touchUpInside)
        backgroundColor = .clear
        contentView.backgroundColor = .white
        shadowView.backgroundColor = UIColor.black.withAlphaComponent(0.2)
    }
    
    func base64ConvertImage(_ imgStr: String ) -> UIImage{
        let data = Data.init(base64Encoded: imgStr, options: .ignoreUnknownCharacters)
        let img = UIImage(data: data!)
        return img ?? UIImage()
    }

}



extension CaptchaView {
    //初始化文字验证码相关view
    func initClickWordView(){
        sliderView.addSubview(textLabel)
        contentView.addSubview(sliderView)
        //底部view坐标
        sliderView.frame = CGRect(x: baseImageView.frame.minX, y: baseImageView.frame.maxY + 20, width: baseImageView.frame.size.width, height: bottomHeight)
        sliderView.backgroundColor       = .white
        sliderView.layer.borderColor = sliderBgColor.cgColor
        sliderView.layer.borderWidth = 0.5
        sliderView.layer.cornerRadius    = 4
        textLabel.frame = CGRect(x: 0, y: 0, width: baseImageView.frame.size.width, height: bottomHeight)
        textLabel.textAlignment = .center
        textLabel.font = UIFont.systemFont(ofSize: 18)
        textLabel.setNeedsLayout();
        setCurrentClickWordResultType(CaptchaResult.progress)
        baseImageView.isUserInteractionEnabled = true
        let pan = UITapGestureRecognizer(target: self, action: #selector(tapBaseImageView(sender:)))
        baseImageView.addGestureRecognizer(pan)
    }
    //图片imgView
    func setClickWordImgView(){
        //图片左边
        let baseImg = base64ConvertImage(repModel.originalImageBase64)
        baseImageView.frame = CGRect(x: (contentView.bounds.size.width - (baseImg.size.width == 0 ? 310.0 : baseImg.size.width))*0.5 , y: topView.frame.maxY + 10, width: baseImg.size.width == 0 ? 310.0 : baseImg.size.width, height: baseImg.size.height == 0 ? 155.0 : baseImg.size.height )
    }
    //文字验证码状态修改
    func setCurrentClickWordResultType(_ currentPuzzleType: CaptchaResult){
        currentClickwordResultType = currentPuzzleType
        switch currentClickwordResultType {
        case .normal:
            var wordList: [String] = []
            if(self.repModel.wordList.count > 0){
                for (_, item) in self.repModel.wordList.enumerated() {
                    wordList.append("\(item)")
                }
                let clickWordStr = wordList.joined(separator: ",")
                self.textLabel.text = "请依次点击【\(clickWordStr)】"
                self.textLabel.textColor = UIColor.black
            }
            sliderView.layer.borderColor = sliderBgColor.cgColor
        case .progress:
            self.tapViewList = []
            sliderView.layer.borderColor = sliderBgColor.cgColor
            self.textLabel.text = "加载中…"
            self.textLabel.textColor = UIColor.black

        case .success:
            // ✓
            sliderView.layer.borderColor = UIColor("4cae4c").cgColor
            self.textLabel.text = "验证成功"
            self.textLabel.textColor = UIColor("4cae4c")

        case .failure:
            printLog("验证失败")
            sliderView.layer.borderColor = UIColor("d9534f").cgColor
            self.textLabel.text = "验证失败"
            self.textLabel.textColor = UIColor("d9534f")

        }
    }
    
    /// 文字点击事件事件
    ///
    /// - Parameter sender: 点击对象
    @objc func tapBaseImageView(sender: UITapGestureRecognizer) {

        let point = sender.location(in: sender.view)
        if self.repModel.wordList.count > 0 && self.tapViewList.count < self.repModel.wordList.count {
            self.tapViewList.append(point)
            let tapView = UILabel()
            let tapSize:CGFloat = 20.0
            tapView.frame = CGRect(x: point.x - tapSize*0.5, y: point.y - tapSize*0.5, width: tapSize, height: tapSize)
            tapView.backgroundColor = UIColor("4cae4c")
            tapView.textColor = .white
            tapView.text = "\(self.tapViewList.count)"
            tapView.textAlignment = .center
            tapView.layer.cornerRadius = tapSize*0.5
            tapView.layer.masksToBounds = true
            self.baseImageView.addSubview(tapView)
        }
        if self.repModel.wordList.count > 0 && self.tapViewList.count == self.repModel.wordList.count {
            checkResult(point)
        }
    }
    
}

//拼图校验视图
extension CaptchaView {
    
    func initPuzzleView(){
        //滑块图片
        baseImageView.addSubview(puzzleImageView)
        
    }
    /// 初始化拼图校验视图
    func _initPuzzleFrame(){
        //图片左边
        let baseImg = base64ConvertImage(repModel.originalImageBase64)
        baseImageView.frame = CGRect(x: (contentView.bounds.size.width - (baseImg.size.width == 0 ? 310.0 : baseImg.size.width))*0.5 , y: topView.frame.maxY + 10, width: baseImg.size.width == 0 ? 310.0 : baseImg.size.width, height: baseImg.size.height == 0 ? 155.0 : baseImg.size.height )
        let puzzleImg = base64ConvertImage(repModel.jigsawImageBase64)
        puzzleImageView.frame = CGRect(x: 0, y: 0, width: puzzleImg.size.width, height: puzzleImg.size.height )
        //底部view坐标
        sliderView.frame = CGRect(x: baseImageView.frame.minX, y: baseImageView.frame.maxY + 20, width: baseImageView.frame.size.width, height: bottomHeight)
        sliderView.backgroundColor       = .white
        sliderView.layer.borderColor = sliderBgColor.cgColor
        sliderView.layer.borderWidth = 0.5

        sliderView.layer.cornerRadius    = 4
        progressView.layer.cornerRadius = 4
        progressView.backgroundColor = sliderBgColor
        progressView.layer.borderWidth = 1
        hintView.frame = CGRect(x: 10, y: 5, width: hintViewWidht, height: bottomHeight - 10)
        thumbView.frame = CGRect(x: puzzleThumbOffsetX, y: 0, width: bottomHeight, height: bottomHeight)
        progressView.frame  = CGRect(x: 0, y: 0, width: thumbView.frame.midX, height: bottomHeight)
        textLabel.frame = CGRect(x: 0, y: 0, width: bottomHeight, height: bottomHeight)
        textLabel.textAlignment = .center
        textLabel.font = UIFont.systemFont(ofSize: 18)

        self.thumbView.layer.borderWidth = 1


        self.setCurrentPuzzleResultType(CaptchaResult.normal)
        promptLabel.frame = sliderView.bounds
        sliderView.addSubview(promptLabel)
        
        //滑块动画view
        sliderView.addSubview(hintView)
        //滑块view显示进度view
        sliderView.addSubview(progressView)
        //内部标题
        thumbView.addSubview(textLabel)
        //滑块view
        sliderView.addSubview(thumbView)
        
        //底部view
        contentView.addSubview(sliderView)
        
        thumbView.isUserInteractionEnabled = true
        let pan = UIPanGestureRecognizer(target: self, action: #selector(slidThumbView(sender:)))
        thumbView.addGestureRecognizer(pan)
//        hintView.layer.setGradient(colors: [sliderBgColor.withAlphaComponent(0), UIColor.white.withAlphaComponent(0.8), sliderBgColor.withAlphaComponent(0)], startPoint: CGPoint(x: 0, y: 0.5), endPoint: CGPoint(x: 1, y: 0.5))
    }
    
    /// 滑动进度条的手势事件
    ///
    /// - Parameter sender: 滑动的手势对象
    @objc func slidThumbView(sender: UIPanGestureRecognizer) {
        self.setCurrentPuzzleResultType(CaptchaResult.progress)
        let point = sender.translation(in: sliderView)
        if lastPointX != .zero {
            let offetX  = point.x - lastPointX
            offsetXList.insert(offetX)
        }
        lastPointX = point.x
        if(thumbView.frame.maxX < sliderView.bounds.width && thumbView.frame.minX > .zero){
            thumbView.transform = CGAffineTransform(translationX: point.x, y: 0)
            progressView.layer.frame = CGRect(x: 0, y: 0, width: thumbView.frame.midX, height: bottomHeight)
            puzzleImageView.transform = CGAffineTransform(translationX: point.x, y: 0)
        }
        if(sender.state == UIGestureRecognizer.State.ended){
            offsetXList.remove(0)
            checkResult(point)
        }
    }
    
    //滑动验证码状态修改  如果需要图片可自行修改
    func setCurrentPuzzleResultType(_ currentPuzzleType: CaptchaResult){
        currentPuzzleResultType = currentPuzzleType
        switch currentPuzzleResultType {
        case .normal:
            // >
            promptLabel.isHidden = false
            self.thumbView.backgroundColor = UIColor.white
            self.thumbView.layer.borderColor = UIColor.gray.cgColor
            progressView.layer.borderColor = UIColor.gray.cgColor
            self.textLabel.text = ">";
        case .progress:
            // >
            promptLabel.isHidden = true
            self.thumbView.backgroundColor = UIColor("337AB7")
            self.thumbView.layer.borderColor = UIColor("337AB7").cgColor
            progressView.layer.borderColor = UIColor("337AB7").cgColor
            self.textLabel.text = ">";

        case .success:
            // ✓
            promptLabel.isHidden = true
            self.thumbView.backgroundColor = UIColor("4cae4c")
            self.thumbView.layer.borderColor = UIColor("4cae4c").cgColor
            progressView.layer.borderColor = UIColor("4cae4c").cgColor
            self.textLabel.text = "✓";

        case .failure:
            // X
            promptLabel.isHidden = true
            self.textLabel.text = "✕";
            self.thumbView.layer.borderColor = UIColor("d9534f").cgColor
            self.thumbView.backgroundColor = UIColor("d9534f")
            progressView.layer.borderColor = UIColor("d9534f").cgColor

        }
    }
    
   
}

/// 延时主线程执行
func t_delay(_ seconds: Double = 2, closure: @escaping () -> ()) {
    let _t = DispatchTime.now() + Double(Int64(Double(NSEC_PER_SEC) * seconds)) / Double(NSEC_PER_SEC)
    DispatchQueue.main.asyncAfter(deadline: _t, execute: closure)
}
