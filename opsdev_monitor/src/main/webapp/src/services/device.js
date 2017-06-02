/**
 * Created by tangzhichao on 2017/6/2.
 */
import  reqwest from '../utils/reqwest';

export async function fetchAll() {
  return reqwest({
    url: '/api/device',
    method: 'get',
    type: 'json',
    data: {}
  });
}
