const webpack = require('webpack');
const CleanWebpackPlugin = require('clean-webpack-plugin');
//服务器配置
exports.devServer = function (options) {
    return {
        watchOptions: {
            aggregateTimeout: 300,
            poll: 1000,
        },

        devServer: {
            historyApiFallback: true,
            hot: true,
            inline: true,
            stats: 'errors-only',
            host: options.host, // Defaults to `localhost`
            port: options.port // Defaults to 8080
        },
        plugins: [
            new webpack.HotModuleReplacementPlugin({
                multiStep: true
            })
        ]
    };
}

//加载器配置
exports.setupModules = function (paths) {
    return {
        module: {
            loaders: [
                {
                    test: /\.jsx?$/,
                    loaders: ['babel?cacheDirectory'],
                    include: paths.app
                },
                {
                    test: /\.css$/,
                    loaders: ['style', 'css'],
                    include: paths
                },
                {
                    test: /\.(woff(2)?|svg|eot|ttf|gif|jpg)(\?.+)?$/, loader: 'file-loader'
                },
                {
                    test: /\.png$/, loader: 'url-loader'
                },
            ]
        }
    };
}

//压缩js
exports.minify = function () {
    return {
        plugins: [
            new webpack.optimize.UglifyJsPlugin({
                compress: {
                    warnings: false
                }
            })
        ]
    };
}


//设置环境变量
exports.setFreeVariable = function (key, value) {
    const env = {};
    env[key] = JSON.stringify(value);

    return {
        plugins: [
            new webpack.DefinePlugin(env)
        ]
    };
}


exports.clean = function (path) {
    return {
        plugins: [
            new CleanWebpackPlugin([path], {
                root: process.cwd()
            })
        ]
    };
}