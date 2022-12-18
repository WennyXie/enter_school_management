const path = require('path')

function resolve(dir) {
  return path.join(__dirname, '.', dir)
}

module.exports = {
  transpileDependencies: true,
  lintOnSave: false,
  productionSourceMap: true,
  devServer:{
    port:8006,
    proxy:{
      '/api':{
        target:"http://localhost:8006",
        ws: true,
        changeOrigin:true
      }
    }
  },
  chainWebpack: (config) => {
    config.module.rules.delete("svg");
    config.module
        .rule('svg-sprite-loader')
        .test(/\.svg$/)
        .include
        .add(resolve('src/icons')) //处理svg目录
        .end()
        .use('svg-sprite-loader')
        .loader('svg-sprite-loader')
        .options({
          symbolId: 'icon-[name]'
        })
  }
}
