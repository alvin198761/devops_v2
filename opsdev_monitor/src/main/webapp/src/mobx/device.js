/**
 * Created by tangzhichao on 2017/3/9.
 */
import {action, observable, computed} from 'mobx';
import {fetchAll} from '../services/device';


class device {
  constructor() {
  }

  @observable device = {
    groups: [],
    devices: []
  }

  @observable type = 0;

  @computed get getDevice() {
    return this.device;
  }

  @action loadDevice = () => {
    const that = this;
    fetchAll().then(res => {
      that.device = res.data;
    });
  }

  @action changeType = (type)=>{
    this.type = type;
  }
}
export default device;
