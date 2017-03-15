/**
 * Created by tangzhichao on 2017/3/15.
 */
import React, {Component, PropTypes} from 'react';
import {observer, inject} from 'mobx-react';
import {Spin} from 'antd';
import {Stat, Frame} from 'g2';
import createG2 from 'g2-react';


@inject('routing')
@inject('chart')
@observer
export default class LineChartDemo extends Component {

  componentWillMount() {
    this.props.chart.loadChart();
  }

  componentWillUnmount(){
    this.props.chart.clearChart();
  }

  render() {
    const _this = this;
    let values = _this.props.chart.getData;
    if (!values) {
      values = []
    }

    var frame = new Frame(values);
    frame = Frame.combinColumns(frame, ['value', 'value1','value2'], 'value', 'city', 'time');

    const LineChart = createG2(chart => {
      chart.source(frame);
      chart.col("time", {
        type: "time",
        alias: "时间",
      });
      chart.col("value", {
        min: 0,
        type: 'linear',
        alias: "结果"
      });
      chart.line().position('time*value').color('city', ['#1f77b4', '#ff7f0e', '#2ca02c']).shape('spline').size(3);
      chart.render();
    });
    return (
      <div>
        报表页面：
        <Spin spinning={_this.props.chart.getLoading}>
          <LineChart
            data={[]}
            width={1200}
            height={400}></LineChart>
        </Spin>
      </div>)
  }
}
