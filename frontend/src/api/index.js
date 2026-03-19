import request from '@/utils/request'

export const authApi = {
  login(data) {
    return request.post('/auth/login', data)
  },
  logout() {
    return request.post('/auth/logout')
  },
  getCurrentUser() {
    return request.get('/auth/currentUser')
  }
}

export const menuApi = {
  list(params) {
    return request.get('/menu/list', { params })
  },
  getAll() {
    return request.get('/menu/all')
  },
  getById(id) {
    return request.get(`/menu/${id}`)
  },
  add(data) {
    return request.post('/menu', data)
  },
  update(id, data) {
    return request.put(`/menu/${id}`, data)
  },
  delete(id) {
    return request.delete(`/menu/${id}`)
  }
}

export const bannerApi = {
  list(params) {
    return request.get('/banner/list', { params })
  },
  getAll() {
    return request.get('/banner/all')
  },
  getById(id) {
    return request.get(`/banner/${id}`)
  },
  add(data) {
    return request.post('/banner', data)
  },
  update(id, data) {
    return request.put(`/banner/${id}`, data)
  },
  delete(id) {
    return request.delete(`/banner/${id}`)
  }
}

export const productApi = {
  list(params) {
    return request.get('/product/list', { params })
  },
  getById(id) {
    return request.get(`/product/${id}`)
  },
  getRecommend() {
    return request.get('/product/recommend')
  },
  add(data) {
    return request.post('/product', data)
  },
  update(id, data) {
    return request.put(`/product/${id}`, data)
  },
  delete(id) {
    return request.delete(`/product/${id}`)
  }
}

export const articleApi = {
  list(params) {
    return request.get('/article/list', { params })
  },
  getById(id) {
    return request.get(`/article/${id}`)
  },
  getLatest() {
    return request.get('/article/latest')
  },
  add(data) {
    return request.post('/article', data)
  },
  update(id, data) {
    return request.put(`/article/${id}`, data)
  },
  delete(id) {
    return request.delete(`/article/${id}`)
  }
}

export const messageApi = {
  list(params) {
    return request.get('/message/list', { params })
  },
  getById(id) {
    return request.get(`/message/${id}`)
  },
  reply(id, data) {
    return request.put(`/message/${id}/reply`, data)
  },
  delete(id) {
    return request.delete(`/message/${id}`)
  },
  submit(data) {
    return request.post('/home/message', data)
  }
}

export const configApi = {
  getAll() {
    return request.get('/config/all')
  },
  update(id, data) {
    return request.put(`/config/${id}`, data)
  }
}

export const homeApi = {
  getMenus() {
    return request.get('/home/menu')
  },
  getBanners() {
    return request.get('/home/banner')
  },
  getRecommendProducts() {
    return request.get('/home/product/recommend')
  },
  getProductCategories() {
    return request.get('/home/product/category')
  },
  getLatestArticles() {
    return request.get('/home/article/latest')
  },
  getArticleCategories() {
    return request.get('/home/article/category')
  },
  getAdvantages() {
    return request.get('/home/advantage')
  },
  getConfig() {
    return request.get('/home/config')
  }
}

export const uploadApi = {
  upload(file) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  }
}

export const userApi = {
  list() {
    return request.get('/user/list')
  },
  getById(id) {
    return request.get(`/user/${id}`)
  },
  add(data) {
    return request.post('/user', data)
  },
  update(id, data) {
    return request.put(`/user/${id}`, data)
  },
  updateStatus(id, status) {
    return request.put(`/user/${id}/status`, { status })
  },
  updatePassword(id, password) {
    return request.put(`/user/${id}/password`, { password })
  },
  delete(id) {
    return request.delete(`/user/${id}`)
  }
}

export const roleApi = {
  list() {
    return request.get('/role/list')
  },
  getById(id) {
    return request.get(`/role/${id}`)
  },
  add(data) {
    return request.post('/role', data)
  },
  update(id, data) {
    return request.put(`/role/${id}`, data)
  },
  delete(id) {
    return request.delete(`/role/${id}`)
  }
}

export const productCategoryApi = {
  list() {
    return request.get('/productCategory/list')
  },
  tree() {
    return request.get('/productCategory/tree')
  },
  getById(id) {
    return request.get(`/productCategory/${id}`)
  },
  add(data) {
    return request.post('/productCategory', data)
  },
  update(id, data) {
    return request.put(`/productCategory/${id}`, data)
  },
  delete(id) {
    return request.delete(`/productCategory/${id}`)
  }
}

export const articleCategoryApi = {
  list() {
    return request.get('/articleCategory/list')
  },
  tree() {
    return request.get('/articleCategory/tree')
  },
  getById(id) {
    return request.get(`/articleCategory/${id}`)
  },
  add(data) {
    return request.post('/articleCategory', data)
  },
  update(id, data) {
    return request.put(`/articleCategory/${id}`, data)
  },
  delete(id) {
    return request.delete(`/articleCategory/${id}`)
  }
}

export const advantageApi = {
  list() {
    return request.get('/advantage/list')
  },
  getById(id) {
    return request.get(`/advantage/${id}`)
  },
  add(data) {
    return request.post('/advantage', data)
  },
  update(id, data) {
    return request.put(`/advantage/${id}`, data)
  },
  delete(id) {
    return request.delete(`/advantage/${id}`)
  }
}
