<template>
        <!-- :style="{display: mode === 'pop'?'none':undefined,
                      position: mode === 'pop'?'absolute':'relative',
                      height: (parseInt(setSize.imgHeight) + vSpace) + 'px',
                      bottom: mode ==='pop'?'42px':undefined}"
                      @mouseover="showImage = true"
                      @mouseout="showImage = true"
                      @mouseover="showImage = true"
                      @mouseout="showImage = true"
                      mode !== 'pop' && -->
    <div style="position: relative;"
         >
        <!-- puzzle的情况 -->
        
        <div v-if="type === '2'" class="verify-img-out"
             v-show="showImage"
             :style="{height: (parseInt(setSize.imgHeight) + vSpace) + 'px'}"
            >
                        <!-- background: 'url(' + imgUrl + imgName[imgRand]+')', -->
            <div class="verify-img-panel" :style="{width: setSize.imgWidth,
                                                   height: setSize.imgHeight,}">
                <img :src="'data:image/png;base64,'+backImgBase" alt="" style="width:100%;height:100%;display:block">
                <div class="verify-refresh" @click="refresh" v-show="showRefresh"><i class="iconfont icon-refresh"></i>
                </div>
                <transition name="tips">
                    <span class="verify-tips" v-if="tipWords" :style="{'background-color': tipsBackColor}">{{tipWords}}</span>
                </transition>
                <!-- <div class="verify-gap"
                     :style="{'width': blockSize.width, 'height': blockSize.height, top: top + 'px', left: left + 'px'}"></div> -->
            </div>
        </div>

        <!-- 公共部分 -->
        <div class="verify-bar-area" :style="{width: setSize.imgWidth,
                                              height: barSize.height,
                                              'line-height':barSize.height}">
            <span class="verify-msg" v-text="text"></span>
            <div class="verify-left-bar"
                 :style="{width: (leftBarWidth!==undefined)?leftBarWidth: barSize.height, height: barSize.height, 'border-color': leftBarBorderColor, transaction: transitionWidth}">
                <span class="verify-msg" v-text="finishText"></span>
                <div class="verify-move-block"
                     @touchstart="start"
                     @mousedown="start"
                     :style="{width: barSize.height, height: barSize.height, 'background-color': moveBlockBackgroundColor, left: moveBlockLeft, transition: transitionLeft}">
                    <i :class="['verify-icon iconfont', iconClass]"
                       :style="{color: iconColor}"></i>
                       <!-- background-image': 'url(' + imgUrl + imgName[imgRand] + ')', -->
                       <!-- 'background': 'url(data:image/png;base64,'+ blockBackImgBase + ')', -->
                    <div v-if="type === '2'"
                         class="verify-sub-block"
                    
                         :style="{'width':Math.ceil(parseInt(setSize.imgWidth)*50/310)+ 'px' ,
                                  'height': setSize.imgHeight,
                                  'top':'-' + (parseInt(setSize.imgHeight) + vSpace) + 'px',
                                  'background-size': setSize.imgWidth + ' ' + setSize.imgHeight,
                                  }"
                         v-show=" showImage">
                         <img :src="'data:image/png;base64,'+blockBackImgBase" alt=""  style="width:100%;height:100%;display:block">
                         </div>
                </div>
            </div>
            <!-- 'top': '-' + (parseInt(setSize.imgHeight) - top + vSpace) + 'px', 原先的 -->
        </div>
    </div>
</template>
<script type="text/babel">
    /**
     * VerifySlide
     * @description 滑块
     * */
    import {aesEncrypt} from "./../utils/ase"
    import {resetSize} from './../utils/util'
    import {reqGet,reqCheck}  from "./../api/index"

    //  "captchaType":"blockPuzzle",
    export default {
        name: 'VerifySlide',
        props: {
            captchaType:{
                type:String,
            },
            type: {
                type: String,
                default: '1'
            },
            //弹出式pop，固定fixed
            mode: {
                type: String,
                default: 'fixed'
            },
            vOffset: {
                type: Number,
                default: 5
            },
            vSpace: {
                type: Number,
                default: 5
            },
            explain: {
                type: String,
                default: '向右滑动完成验证'
            },
            imgUrl: {
                type: String,
                default: 'http://via.placeholder.com/'
            },
            imgName: {
                type: Array,
                default() {
                    return ['350x150', '350x450']
                }
            },
            imgSize: {
                type: Object,
                default() {
                    return {
                        width: '310px',
                        height: '155px'
                    }
                }
            },
            blockSize: {
                type: Object,
                default() {
                    return {
                        width: '50px',
                        height: '50px'
                    }
                }
            },
            barSize: {
                type: Object,
                default() {
                    return {
                        width: '310px',
                        height: '40px'
                    }
                }
            }
        },
        data() {
            return {
                backImgBase:'',      //验证码背景图片
                blockBackImgBase:'', //验证滑块的背景图片
                backToken:"",        //后端返回的唯一token值
                startMoveTime:"",    //移动开始的时间
                endMovetime:'',      //移动结束的时间
                tipsBackColor:'',    //提示词的北京颜色
                tipWords:'',
                imgRand: 0,
                text: '',
                finishText:'',
                setSize: {
                    imgHeight: 0,
                    imgWidth: 0,
                    barHeight: 0,
                    barWidth: 0
                },
                top: 0,
                left: 0,
                showImage: true,
                moveBlockLeft: undefined,
                leftBarWidth: undefined,
                // 移动中样式
                moveBlockBackgroundColor: undefined,
                leftBarBorderColor: '#ddd',
                iconColor: undefined,
                iconClass: 'icon-right',
                status: false,	//鼠标状态
                isEnd: false,		//是够验证完成
                showRefresh: true,
                transitionLeft: '',
                transitionWidth: ''
            }
        },
        computed: {
            barArea() {
                return this.$el.querySelector('.verify-bar-area')
            },
            resetSize() {
                return resetSize
            }
        },
        methods: {
            init() {
                this.text = this.explain
                // this.imgRand = Math.floor(Math.random() * this.imgName.length);			//随机的背景图片
                this.getPictrue();

                this.$nextTick(() => {
                    let setSize = this.resetSize(this)	//重新设置宽度高度
                    // 监听的问题
                    for (let key in setSize) {
                        this.$set(this.setSize, key, setSize[key])
                    }
                    // this.randSet()
                    this.$parent.$emit('ready', this)
                })

                var _this = this

                window.removeEventListener("touchmove", function (e) {
                    _this.move(e);
                });
                window.removeEventListener("mousemove", function (e) {
                    _this.move(e);
                });

                //鼠标松开
                window.removeEventListener("touchend", function () {
                    _this.end();
                });
                window.removeEventListener("mouseup", function () {
                    _this.end();
                });

                window.addEventListener("touchmove", function (e) {
                    _this.move(e);
                });
                window.addEventListener("mousemove", function (e) {
                    _this.move(e);
                });

                //鼠标松开
                window.addEventListener("touchend", function () {
                    _this.end();
                });
                window.addEventListener("mouseup", function () {
                    _this.end();
                });

            },

            //鼠标按下
            start: function (e) {
                e = e || window.event
                this.startMoveTime = +new Date();   //开始滑动的时间
                if (this.isEnd == false) {
                    this.text = ''
                    this.moveBlockBackgroundColor = '#337ab7'
                    this.leftBarBorderColor = '#337AB7'
                    this.iconColor = '#fff'
                    e.stopPropagation();
                    this.status = true;
                }
            },
            //鼠标移动
            move: function (e) {
                e = e || window.event
                if (this.status && this.isEnd == false) {
                    // if (this.mode == 'pop') {
                    //    this.showImage = true
                    // }

                    if (!e.touches) {   //兼容移动端
                        var x = e.clientX;
                    } else {     //兼容PC端
                        var x = e.touches[0].pageX;
                    }
                    // var bar_area_left = this.getLeft(this.barArea);
                    var bar_area_left = this.barArea.getBoundingClientRect().left;
                    var move_block_left = x - bar_area_left //小方块相对于父元素的left值

                    if (this.type !== '1') {		//图片滑动
                        if (move_block_left >= this.barArea.offsetWidth - parseInt(parseInt(this.blockSize.width) / 2) - 2) {
                            move_block_left = this.barArea.offsetWidth - parseInt(parseInt(this.blockSize.width) / 2) - 2;
                        }

                    } else {		//普通滑动
                        if (move_block_left >= this.barArea.offsetWidth - parseInt(parseInt(this.barSize.height) / 2) + 3) {
                            this.finishText = '松开验证'
                            move_block_left = this.barArea.offsetWidth - parseInt(parseInt(this.barSize.height) / 2) + 3;
                        } else {
                            this.finishText = ''
                        }
                    }

                    if (move_block_left <= 0) {
                        move_block_left = parseInt(parseInt(this.blockSize.width) / 2);
                    }

                    //拖动后小方块的left值
                    this.moveBlockLeft = (move_block_left - parseInt(parseInt(this.blockSize.width) / 2) ) + "px"
                    this.leftBarWidth = (move_block_left - parseInt(parseInt(this.blockSize.width) / 2)) + "px"
                }
            },

            //鼠标松开
            end: function () {
                this.endMovetime = +new Date(); 
                var _this = this;
//                判断是否重合
                if (this.status && this.isEnd == false) {
                    if (this.type !== '1') {		//图片滑动
                        var moveLeftDistance = parseInt((this.moveBlockLeft || '').replace('px', ''));
                        moveLeftDistance = moveLeftDistance * 310/ parseInt(this.setSize.imgWidth)

                        let data = {
                            captchaType:this.captchaType,
                            "pointJson":aesEncrypt(JSON.stringify({x:moveLeftDistance,y:5.0})),
                            "token":this.backToken
                        }
                        reqCheck(data).then(res=>{
                            if (res.repCode == "0000") {
                                this.moveBlockBackgroundColor = '#5cb85c'
//                              this.htmlDoms.left_bar.css({'border-color': '#5cb85c', 'background-color': '#fff'});
                                this.leftBarBorderColor = '#5cb85c'
                                this.iconColor = '#fff'
                                this.iconClass = 'icon-check'
                                this.showRefresh = false
                                this.isEnd = true;   
                                if (this.mode=='pop') {
                                    setTimeout(()=>{
                                        this.$parent.clickShow = false;
                                        this.refresh();
                                    },1500)
                                }
                                // this.tipsBackColor = '#5cb85c'
                                this.tipsBackColor = 'rgb(92, 184, 92,.5)'
                                this.tipWords = `${((this.endMovetime-this.startMoveTime)/1000).toFixed(2)}s验证成功`

                                var captchaVerification = aesEncrypt(this.backToken+'---'+JSON.stringify({x:moveLeftDistance,y:5.0}))
                                setTimeout(()=>{
                                    this.tipWords = ""
                                    this.$parent.closeBox();
                                    this.$parent.$emit('success', {captchaVerification})
                                },1000)
                            }else{
                                this.moveBlockBackgroundColor = '#d9534f'
                                this.leftBarBorderColor = '#d9534f'
                                this.iconColor = '#fff'
                                this.iconClass = 'icon-close'
                                // this.tipsBackColor = '#d9534f'
                                this.tipsBackColor = 'rgb(217, 83, 79,.5)'
                                setTimeout(function () {
                                    _this.refresh();
                                }, 1000);
                                this.$parent.$emit('error',this)
                                this.tipWords = "验证失败"
                                setTimeout(()=>{
                                     this.tipWords = ""
                                },1000)
                            }
                        })

                    } else {		//普通滑动

                        if (parseInt((this.moveBlockLeft || '').replace('px', '')) >= (parseInt(this.setSize.barWidth) - parseInt(this.barSize.height) - parseInt(this.vOffset))) {
                            this.moveBlockBackgroundColor = '#5cb85c'
//                            this.htmlDoms.left_bar.css({
//                                'color': '#4cae4c',
//                                'border-color': '#5cb85c',
//                                'background-color': '#fff'
//                            });
                            this.leftBarBorderColor = '#5cb85c'
                            this.iconColor = '#fff'
                            this.iconClass = 'icon-check'
                            this.showRefresh = false
                            this.finishText = '验证成功'
                            this.isEnd = true;
                            this.$parent.$emit('success', this)
                        } else {
                            this.finishText = ''
                            this.moveBlockBackgroundColor = '#d9534f'
                            this.leftBarBorderColor = '#d9534f'
                            this.iconColor = '#fff'
                            this.iconClass = 'icon-close'
                            this.isEnd = true;

                            setTimeout(function () {
                                _this.finishText = ''
                                _this.refresh()
                                _this.isEnd = false
                            }, 400);

                            this.$parent.$emit('error', this)
                        }
                    }

                    this.status = false;
                }
            },

            //随机出生点位
            randSet: function () {
                var rand1 = Math.floor(Math.random() * 9 + 1);
                var rand2 = Math.floor(Math.random() * 9 + 1);
                var top = rand1 * parseInt(this.setSize.imgHeight) / 15 + parseInt(this.setSize.imgHeight) * 0.1;
                var left = rand2 * parseInt(this.setSize.imgWidth) / 15 + parseInt(this.setSize.imgWidth) * 0.1;

                this.top = top
                this.left = left
            },

            refresh: function () {
                this.showRefresh = true
                this.finishText = ''

                this.transitionLeft = 'left .3s'
                this.moveBlockLeft = 0

                this.leftBarWidth = undefined
                this.transitionWidth = 'width .3s'

                this.leftBarBorderColor = '#ddd'
                this.moveBlockBackgroundColor = '#fff'
                this.iconColor = '#000'
                this.iconClass = 'icon-right'

                // this.randSet()
                // this.imgRand = Math.floor(Math.random() * this.imgName.length);			//随机的背景图片
                this.getPictrue()

                // @todo 检查重启
//                this.$element.find('.verify-img-panel').css({'background': 'url('+ this.options.imgUrl +this.options.imgName[this.img_rand]+')', 'background-size': this.setSize.img_width + ' '+ this.setSize.img_height});
//                this.$element.find('.verify-sub-block').css({'background-image': 'url('+ this.options.imgUrl +this.options.imgName[this.img_rand]+')', 'background-size': this.setSize.img_width + ' '+ this.setSize.img_height});
//
                this.isEnd = false

                setTimeout(() => {
                    this.transitionWidth = ''
                    this.transitionLeft = ''
                    this.text = this.explain
                }, 300)
            },

            //获取left值
            getLeft: function (node) {
                let leftValue = 0;
                while (node) {
                    leftValue += node.offsetLeft;
                    node = node.offsetParent;
                }
                let finalvalue = leftValue;
                return finalvalue;
            },

            // 请求背景图片和验证图片
            // 请求背景图片和验证图片
            getPictrue(){
                let data = {
                    captchaType:this.captchaType
                }
                reqGet(data).then(res=>{
                    if (res.repCode == "0000") {
                        this.backImgBase = res.repData.originalImageBase64
                        this.blockBackImgBase = res.repData.jigsawImageBase64
                        this.backToken = res.repData.token
                    }else{
                        this.tipWords = res.repMsg;
                    }
                })
            },
        },
        watch: {
            // type变化则全面刷新
            type: {
                immediate: true,
                handler() {
                    this.init()
                }
            }
        },
        mounted() {
            // 禁止拖拽
            this.$el.onselectstart = function () {
                return false
            }
        },
        // created(){
            
        // },
        i18n: {
            messages: {
                'en-US': {},
                'zh-CN': {}
            }
        }
    }
</script>

