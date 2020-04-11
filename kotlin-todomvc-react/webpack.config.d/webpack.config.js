const HtmlWebpackPlugin = require('html-webpack-plugin');

config.plugins.push(new HtmlWebpackPlugin({
    inject: true,
    template: "../../../processedResources/Js/main/index.template.html",
}));

config.module.rules.push({
    test: /\.css$/,
    use: [
        { loader: "style-loader" },
        { loader: "css-loader" }
        // https://youtrack.jetbrains.com/issue/KT-32721
        // {loader: 'style-loader!css-loader'}
    ],
});

config.resolveLoader = config.resolve

config.devServer = {
    ...config.devServer || {},
    watchOptions: {
        "aggregateTimeout": 3000,
        "poll": 1000
    },
    historyApiFallback: true,
};
