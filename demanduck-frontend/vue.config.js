const { defineConfig } = require('@vue/cli-service')
console.log('启动环境', process.env.NODE_ENV)
module.exports = defineConfig({
    publicPath: '/',
    transpileDependencies: true,
    devServer: {
        port: 8043,
        proxy: {
            '/api': {
                target: 'http://localhost:8045',
                ws: true,
                changeOrigin: true,
            }
        }
    }
})
