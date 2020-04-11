const HtmlWebpackPlugin = require('html-webpack-plugin');

config.plugins.push(new HtmlWebpackPlugin({
    inject: true,
    template: "../../../processedResources/js/main/index.html",
}));
