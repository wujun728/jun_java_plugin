layui.define([], function (exports) {
	var MOD_NAME = 'tinymce';
	var modFile = layui.cache.modules['tinymce'];
	var modPath = modFile.substr(0, modFile.lastIndexOf('.'));
	var plugin_filename = 'tinymce.min.js'//插件名称
	var settings = {
		base_url:modPath,
		images_upload_url: '/admin/api/upload/sourse/tinymce',//图片上传接口
		images_upload_credentials:true,
		placeholder: '请输入内容...',
		language: 'zh_CN',//语言
		suffix: '.min',
		selector: '#gouguedit',
		quickbars_insert_toolbar:'',
		quickbars_selection_toolbar: 'cut copy | bold italic underline strikethrough ',
		plugins: 'code quickbars print preview searchreplace autolink fullscreen image link media codesample table charmap hr advlist lists wordcount imagetools axupimgs importword upfile',
		toolbar: 'code undo redo | forecolor backcolor bold italic underline strikethrough removeformat | alignleft aligncenter alignright alignjustify outdent indent | bullist numlist table blockquote link image media codesample axupimgs importword upfile| formatselect fontselect fontsizeselect',
		resize: false,
		elementpath: false,
		branding: false,
		statusbar: false,
		convert_urls: false,// 附件路径无需要转换为相对路径。
		contextmenu_never_use_native: true,
		paste_data_images: true,
		menubar: 'file edit insert format table',
		menu: {
			file: { title: '文件', items: 'preview fullscreen | wordcount' },
			edit: { title: '编辑', items: 'undo redo | cut copy paste pastetext selectall | searchreplace' },
			format: {
				title: '格式',
				items: 'bold italic underline strikethrough superscript subscript | formats | forecolor backcolor | removeformat'
			},
			table: { title: '表格', items: 'inserttable tableprops deletetable | cell row column' },
		},
		codesample_languages: [
			{text: 'HTML', value: 'html'},
			{text: 'JavaScript', value: 'javascript'},
			{text: 'CSS', value: 'css'},
			{text: 'PHP', value: 'php'},
			{text: 'Java', value: 'java'},
			{text: 'Shell', value: 'shell'},
			{text: 'SQL', value: 'sql'},
			{text: 'JOSN', value: 'json'},
			{text: 'Python', value: 'python'},
			{text: 'XML', value: 'xml'},
			{text: 'Golang', value: 'go'},
			{text: 'Objective-c', value: 'objective-c'},
		],
		fontsize_formats: '12px 14px 16px 18px 24px 36px 48px 56px 72px',
		font_formats: '微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;Comic Sans MS=comic sans ms,sans-serif;Courier New=courier new,courier;Georgia=georgia,palatino;Helvetica=helvetica;Impact=impact,chicago;Symbol=symbol;Tahoma=tahoma,arial,helvetica,sans-serif;Terminal=terminal,monaco;Times New Roman=times new roman,times;Verdana=verdana,geneva;Webdings=webdings;Wingdings=wingdings,zapf dingbats;知乎配置=BlinkMacSystemFont, Helvetica Neue, PingFang SC, Microsoft YaHei, Source Han Sans SC, Noto Sans CJK SC, WenQuanYi Micro Hei, sans-serif;小米配置=Helvetica Neue,Helvetica,Arial,Microsoft Yahei,Hiragino Sans GB,Heiti SC,WenQuanYi Micro Hei,sans-serif',
		setup: function(editor) {
			console.log("ID为: " + editor.id + " 的编辑器即将初始化.");
		},
		init_instance_callback : function(editor) {
			console.log("ID为: " + editor.id + " 的编辑器已初始化完成.");
		}
	};

	var editor = {
		render: function (options) {
			loadScript();
			var opts = $.extend({}, settings, options), edit = this.get(opts.elem);
			if (edit) {
				edit.destroy();
			}
			tinymce.init(opts);
			return this.get(opts.elem);
		},
		// 获取ID对应的编辑器对象
		get: function (elem) {
			loadScript();
			if (elem && /^#|\./.test(elem)) {
				var id = elem.substr(1);
				var edit = tinymce.editors[id];
				return edit;
			} else {
				return false;
			}
		}
	}
	function loadScript() {
		if (typeof tinymce == 'undefined') {
			$.ajax({//获取插件
				url: modPath + '/' + plugin_filename,
				dataType: 'script',
				cache: true,
				async: false,
			});
		}
	}
	exports(MOD_NAME, editor);
});
