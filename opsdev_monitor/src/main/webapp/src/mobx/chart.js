/**
 * Created by tangzhichao on 2017/3/15.
 */
import {action, observable, computed} from 'mobx';
import reqwest from '../utils/reqwest';

class chart {

  constructor() {
  }

  @observable data = [];
  @observable loading = false;

  @computed get getData() {
    return this.data;
  }

  @computed get getLoading() {
    return this.loading;
  }

  @action loadChart() {
    let _this = this;
    _this.loading = true;
    reqwest({
      url: '/api/chart',
      method: 'GET',
      type: 'json',
      contentType: 'application/json'
    }).then(res => {
      _this.data = res;
      _this.loading = false;
    }).catch(res => {
      _this.data = [];
      _this.loading = false;
    })
  }

  @action clearChart() {
    this.data = [];
  }

}
export default chart;
