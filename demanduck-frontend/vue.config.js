const { defineConfig } = require('@vue/cli-service')
console.log('启动环境', process.env.NODE_ENV)
module.exports = defineConfig({
    configureWebpack: {
        devtool: 'source-map'
    },
    publicPath: '/',
    transpileDependencies: true,
    pages: {
        index: {
            entry: 'src/main.js',
            title: 'Demanduck',
        }
    },
    devServer: {
        port: 8043,
        proxy: {
            '/api': {
                target: 'http://localhost:8045',
                pathRewrite: {'^/api': ''},
                ws: true,
                changeOrigin: true,
            }
        }
    }
})
