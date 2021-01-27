/**
 * bootstrap版评论框
 * 
 * @date 2018-01-05 10:57
 * @author zhyd(yadong.zhang0415#gmail.com)
 * @link https://github.com/zhangyd-c
 */
$.extend({
	comment: {
		submit: function(target) {
			var $this = $(target);
			$this.button('loading');

			$('#detail-modal').modal('show');
			$(".close").click(function() {
				setTimeout(function() {
					$this.html("<i class='fa fa-close'></i>取消操作...");
					setTimeout(function() {
						$this.button('reset');
					}, 1000);
				}, 500);
			});
			// 模态框抖动
			//		$('#detail-modal .modal-content').addClass("shake");
			$("#detail-form-btn").click(function() {
				$.ajax({
					type: "get",
					url: "./server/comment.json",
					async: true,
					success: function(json) {
						if(json.statusCode == 200) {
							console.log(json.message);
						} else {
							console.error(json.message);
						}
						$('#detail-modal').modal('hide');

						setTimeout(function() {
							$this.html("<i class='fa fa-check'></i>" + json.message);
							setTimeout(function() {
								$this.button('reset');
								window.location.reload();
							}, 1000);
						}, 1000);
					},
					error: function(data) {
						console.error(data);
					}
				});
			});
		},
		reply: function(pid, c) {
			console.log(pid);
			$('#comment-pid').val(pid);
			$('#cancel-reply').show();
			$('.comment-reply').show();
			$(c).hide();
			$(c).parents('.comment-body').append($('#comment-post'));
			//			$(c).parent().parent().parent().append($('#comment-post'));
		},
		cancelReply: function(c) {
			$('#comment-pid').val("");
			$('#cancel-reply').hide();
			$(c).parents(".comment-body").find('.comment-reply').show();
			//			$(c).parent().parent().parent().find('.comment-reply').show();
			$("#comment-place").append($('#comment-post'));
		}
	}
});

$(function() {
	$('[data-toggle="tooltip"]').tooltip();
	$("#comment-form-btn").click(function() {
		$.comment.submit($(this));
	});
})