// import Vue from 'vue'
// import SvgIcon from '@/SvgIcon'

// Vue.component('svg-icon', SvgIcon)

const requireAll = requireContext => requireContext.keys().map(requireContext)
const req = require.context('./', false, /\.svg$/)
requireAll(req)
