<script>
	export default {
		onLaunch: function() {
			// #ifdef H5
				document.addEventListener("touchmove",function(e){
					e.preventDefault();
				},false);//禁止页面滑动
			// #endif
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/* #ifdef H5 */
		html,body{
			font-size: 16px;
			touch-action: pan-y;
		}
	/* #endif */
	/*每个页面公共css */
</style>
