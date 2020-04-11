config.devServer = {
    ...config.devServer || {},
    watchOptions: {
        "aggregateTimeout": 5000,
        "poll": 1000
    },
    historyApiFallback: true,
};