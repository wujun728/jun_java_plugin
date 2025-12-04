<template>
    <div class="container">
      <h1>文本格式转换工具</h1>
      <div class="converter-box">
        <div class="input-group">
          <span class="label">输入文本（支持多行粘贴）：</span>
          <textarea 
            v-model="inputText" 
            rows="8"
            placeholder="请输入/粘贴需要转换的文本"
            class="input-field"
          ></textarea>
        </div>
  
        <div class="action-buttons">
          <button @click="convert('snake')" class="btn">转下划线</button>
          <button @click="convert('camel')" class="btn">转驼峰</button>
          <button @click="convert('space')" class="btn">空格转下划线</button>
          <button @click="convert('lower')" class="btn">全小写</button>
          <button @click="convert('upper')" class="btn">全大写</button>
          <button @click="clearAll" class="btn btn-clear">清空</button>
        </div>
  
        <div class="result-group" v-if="showResult">
          <div class="result-item">
            <div class="result-header">
              <span class="label">转换结果：</span>
              <div>
                <button @click="copyResult" class="copy-btn">复制</button>
                <button @click="clearResult" class="copy-btn btn-clear">清空结果</button>
              </div>
            </div>
            <pre class="output">{{ currentResult }}</pre>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        inputText: '',
        currentResult: '',
        showResult: false,
        lastConversionType: ''
      };
    },
    methods: {
      convert(type) {
        this.lastConversionType = type;
        this.showResult = true;
        
        const lines = this.inputText.split('\n');
        this.currentResult = lines.map(line => {
          switch(type) {
            case 'snake':
              return this.camelToSnake(line);
            case 'camel':
              return this.snakeToCamel(line);
            case 'space':
              return line.replace(/\s+/g, '_');
            case 'lower':
              return line.toLowerCase();
            case 'upper':
              return line.toUpperCase();
            default:
              return line;
          }
        }).join('\n');
      },
  
      camelToSnake(str) {
        return str.replace(/([A-Z])/g, '_$1').toLowerCase().replace(/^_/, '');
      },
  
      snakeToCamel(str) {
        return str.toLowerCase().replace(/(_\w)/g, match => match[1].toUpperCase());
      },
  
      clearAll() {
        this.inputText = '';
        this.currentResult = '';
        this.showResult = false;
      },
  
      clearResult() {
        this.currentResult = '';
        this.showResult = false;
      },
  
      copyResult() {
        if (!this.currentResult) {
          alert('没有可复制的内容');
          return;
        }
        
        const textarea = document.createElement('textarea');
        textarea.value = this.currentResult;
        document.body.appendChild(textarea);
        textarea.select();
        
        try {
          document.execCommand('copy');
          this.$modal.msgSuccess("复制成功!");
        } catch (err) {
          this.$modal.msgError("复制失败，请手动选择文本复制");
        }
        
        document.body.removeChild(textarea);
      }
    }
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 800px;
    margin: 2rem auto;
    padding: 0px 20px;
    font-family: Arial, sans-serif;
  }
  
  h1 {
    text-align: center;
    color: #2c3e50;
    margin-bottom: 1.5rem;
  }
  
  .converter-box {
    background: #f8f9fa;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }
  
  .input-group {
    margin-bottom: 1.5rem;
  }
  
  .input-field {
    width: 98%;
    padding: 5px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 16px;
    margin-top: 8px;
    min-height: 100px;
    resize: vertical;
    line-height: 1.5;
  }
  .input-field:focus {
  outline: none;
  box-shadow: 0 0 0 2px rgba(0, 120, 215, 0.2); /* 可选：添加自定义焦点样式 */
}
  .action-buttons {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(110px, 1fr));
    gap: 10px;
    margin: 1.5rem 0;
  }
  
  .btn {
    padding: 10px 12px;
    background: #3498db;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background 0.3s;
    font-size: 14px;
  }
  
  .btn:hover {
    background: #2980b9;
  }
  
  .btn-clear {
    background: #e74c3c !important;
  }
  
  .btn-clear:hover {
    background: #c0392b !important;
  }
  
  .result-group {
    margin-top: 1.5rem;
    animation: fadeIn 0.3s ease;
  }
  
  .result-item {
    background: white;
    border-radius: 4px;
    padding: 12px;
    box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  }
  
  .result-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;
  }
  
  .label {
    font-weight: bold;
    color: #34495e;
    font-size: 15px;
  }
  
  .result-header > div {
    display: flex;
    gap: 8px;
  }
  
  .copy-btn {
    padding: 6px 10px;
    background: #27ae60;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    font-size: 13px;
  }
  
  .copy-btn:hover {
    background: #219a52;
  }
  
  .output {
    padding: 12px;
    background: #f8f9fa;
    border: 1px solid #eee;
    border-radius: 4px;
    white-space: pre-wrap;
    word-break: break-all;
    line-height: 1.6;
    max-height: 300px;
    overflow-y: auto;
    font-family: Consolas, Monaco, monospace;
  }
  
  @keyframes fadeIn {
    from { opacity: 0; transform: translateY(10px); }
    to { opacity: 1; transform: translateY(0); }
  }
  
  @media (max-width: 600px) {
    .action-buttons {
      grid-template-columns: 1fr 1fr;
    }
    
    .container {
      padding: 15px;
    }
  }
  </style>