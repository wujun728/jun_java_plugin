const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const merge = require('webpack-merge');
const validate = require('webpack-validator');

const parts = require('./partial/parts');
const PATHS = {
    app: path.join(__dirname, 'app'),
    build: path.join(__dirname, '../src/main/resources/static')
};

const common = {
    entry: {
        app: PATHS.app,
        // vendor: ['react']
    },
    resolve: {
        extensions: ['', '.js', '.jsx']
    },
    output: {
        path: PATHS.build,
        filename: '[name].[hash].js',
        chunkFilename: '[hash].js'
    },
    plugins: [
        new HtmlWebpackPlugin({
            title: 'ReactWeb'
        }),
        new webpack.ProvidePlugin({
            $:"jquery",
            jQuery:"jquery",
            "window.jQuery":"jquery"
        })
    ]
}


var config;

switch (process.env.npm_lifecycle_event) {
    case 'build':
        config = merge(
            common,
            parts.setupModules(PATHS.app),
            parts.clean(PATHS.build))
        break;
    default:
        config = merge(
            common,
            parts.devServer({
                host: process.env.HOST,
                port: process.env.PORT
            }),
            parts.setupModules(PATHS.app),
            parts.clean(PATHS.build)
        );
}

module.exports = validate(config);
