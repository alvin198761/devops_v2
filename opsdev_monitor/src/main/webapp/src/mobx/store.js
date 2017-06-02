/**
 * Created by tangzhichao on 2017/3/9.
 */
import app from './app';
import chart from './chart';
import device from './device';
import {useStrict} from 'mobx';
useStrict(false)

const store = {
  app: new app(),
  chart: new chart(),
  device: new device(),
};

export default store;
