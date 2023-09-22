import ElementPlus from 'element-plus'
import '../element-variables.scss'
import locale from 'element-plus/dist/locale/zh-cn'

export default (app) => {
  app.use(ElementPlus, { locale })
}
