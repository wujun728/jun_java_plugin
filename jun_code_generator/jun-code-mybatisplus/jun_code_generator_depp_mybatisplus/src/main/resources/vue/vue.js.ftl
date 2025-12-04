import axios from '_l/api.request'

/**
 * 添加${table.comment}
 * @param ${entity?uncap_first}
 * @returns {ClientHttp2Stream | * | AxiosPromise<any> | ClientRequest | void}
 */
export const add${entity} = (${entity?uncap_first}) => {
  return axios.request({
    url: '/${entity?uncap_first}/save',
    method: 'post',
    data: ${entity?uncap_first}
  })
}

/**
 * 修改${table.comment}
 * @param ${entity?uncap_first}
 * @returns {ClientHttp2Stream | * | AxiosPromise<any> | ClientRequest | void}
 */
export const update${entity} = (${entity?uncap_first}) => {
  return axios.request({
    url: '/${entity?uncap_first}/update',
    method: 'post',
    data: ${entity?uncap_first}
  })
}

/**
 * 删除${table.comment}
 * @param ${entity?uncap_first}Id
 * @returns {ClientHttp2Stream | * | AxiosPromise<any> | ClientRequest | void}
 */
export const delete${entity}ById = (${entity?uncap_first}Id) => {
  return axios.request({
    url: '/${entity?uncap_first}/delete',
    method: 'post',
    params: {
      id: ${entity?uncap_first}Id
    }
  })
}

/**
 * 批量删除${table.comment}
 * @param ${entity?uncap_first}Ids
 * @returns {ClientHttp2Stream | * | AxiosPromise<any> | ClientRequest | void}
 */
export const deleteBatch${entity}ById = (${entity?uncap_first}Ids) => {
  return axios.request({
    url: '/${entity?uncap_first}/deleteBatch',
    method: 'post',
    data: ${entity?uncap_first}Ids
  })
}
