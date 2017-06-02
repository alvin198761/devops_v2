/**
 * Created by tangzhichao on 2017/6/2.
 */
import React, {Component, PropTypes} from 'react';
import {observer, inject} from 'mobx-react';
import {Layout, Menu, Icon, Row, Col, Button, Spin} from 'antd';
import {Stat, Frame} from 'g2';
import createG2 from 'g2-react';

const SubMenu = Menu.SubMenu;
const {Header, Footer, Sider, Content} = Layout;

@inject('routing')
@observer
class ChartItem extends Component {

  constructor(props) {
    super(props);
  }

  componentWillMount() {

  }

  render() {
    const data = [
      {year: 2007, area: '亚太地区', profit: 1 * 0.189},
      {year: 2007, area: '非洲及中东', profit: 100 * 0.042}
    ];
    const plotCfg = {
      margin: 0,
    }
    const Pie = createG2(chart => {
      chart.coord('theta',{
        radius: 1,
        inner: 0.65
      });
      chart.intervalStack().position(Stat.summary.proportion()).color('area');
      chart.render();
    });
    return (
      <div>
        <Pie
          data={data}
          width={200}
          height={200}
          plotCfg={plotCfg }
        />
        <div>数据：{this.props.deviceId}</div>
      </div>
    );
  }
}

ChartItem.propTypes = {};

export default ChartItem;
