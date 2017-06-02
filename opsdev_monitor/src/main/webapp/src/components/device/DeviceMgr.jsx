/**
 * Created by tangzhichao on 2017/4/17.
 */
import './DeviceMgr.css';
import React, {Component, PropTypes} from 'react';
import {observer, inject} from 'mobx-react';
import {Layout, Menu, Icon, Row, Col, Collapse, Table, Input} from 'antd';
import ChartItem from './ChartItem';
const SubMenu = Menu.SubMenu;
const {Header, Footer, Sider, Content} = Layout;
const Panel = Collapse.Panel;

@inject('routing')
@inject('device')
@observer
class DeviceMgr extends Component {
  componentWillMount() {
    this.props.device.loadDevice();
  }

  componentWillUnmount() {

  }

  render() {

    const clickListener = ({item, key, keyPath}) => {
      console.log(key)
    }

    const groupClickListener = (id, {eventKey, domEvent}) => {
      console.log(id)
    }

    const columns = [{
      title: 'Name',
      dataIndex: 'name',
      render: text => <a href="#">{text}</a>,
    }, {
      title: 'Age',
      dataIndex: 'age',
    }, {
      title: 'Address',
      dataIndex: 'address',
    }];
    const data = [{
      key: '1',
      name: 'John Brown',
      age: 32,
      address: 'New York No. 1 Lake Park',
    }, {
      key: '2',
      name: 'Jim Green',
      age: 42,
      address: 'London No. 1 Lake Park',
    }, {
      key: '3',
      name: 'Joe Black',
      age: 32,
      address: 'Sidney No. 1 Lake Park',
    }, {
      key: '4',
      name: 'Disabled User',
      age: 99,
      address: 'Sidney No. 1 Lake Park',
    }];

// rowSelection object indicates the need for row selection
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => {
        console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
      },
      getCheckboxProps: record => ({
        disabled: record.name === 'Disabled User',    // Column configuration not to be checked
      }),
    };

    return (
      <div>
        <Layout>
          <Sider>
            <Menu
              theme={"dark"}
              style={{width: 240}}
              mode="inline"
              onClick={clickListener.bind(this)}
            >
              <Menu.Item key="plus">
                设备组
                <span style={{float: 'right', fontSize: 22}}><Icon type="plus-circle"/></span>
              </Menu.Item>
              {this.props.device.getDevice.groups.map(item =>
                <SubMenu key={item.id} title={<span><Icon type="database"/>{item.title}</span>}
                         onTitleClick={groupClickListener.bind(this, item.id)}>
                  {item.children.map(child => <Menu.Item key={child.id }><Icon type="appstore"/>{child.title}
                  </Menu.Item>) }
                </SubMenu>)}
              <SubMenu key="device" title={<span><Icon type="appstore"/><span>未分组</span></span>}>
                {this.props.device.getDevice.devices.map(item => <Menu.Item key={item.id}>{item.title}</Menu.Item>)}
              </SubMenu>
            </Menu>
          </Sider>
          <Content style={{marginLeft: '50px'}}>
            <header>
              {this.props.device.type == 0 &&
              <Row className="metrics">
                <Col span={8}><Icon type="layout" style={{fontSize: '24px', marginLeft: '15px'}}/> 主机数：1</Col>
                <Col span={8}><Icon type="layout" style={{fontSize: '24px', marginLeft: '15px'}}/> 指标数：1</Col>
                <Col span={8}><Icon type="layout" style={{fontSize: '24px', marginLeft: '15px'}}/> 主机组数：7</Col>
              </Row>}
              {this.props.device.type == 1 && <Row className="metrics">
                <Col span={12}><Icon type="layout" style={{fontSize: '24px', marginLeft: '15px'}}/> 主机数：1</Col>
                <Col span={12}><Icon type="layout" style={{fontSize: '24px', marginLeft: '15px'}}/> 指标数：1</Col>
              </Row>}
              <Collapse defaultActiveKey={['1']}>
                <Panel header="主机状态概览" key="1" style={{fontSize: '20px', fontWeight: 'bold'}}>
                  <Row>
                    <Col span={8}><ChartItem deviceId={1}></ChartItem></Col>
                    <Col span={8}><ChartItem deviceId={2}></ChartItem></Col>
                    <Col span={8}><ChartItem deviceId={3}></ChartItem></Col>
                  </Row>
                </Panel>
              </Collapse>
            </header>
            <div>
              <Row style={{height: '50px', lineHeight: '50px'}}>
                <Col span={16}>
                  <span style={{fontSize: 14, fontWeight: 'bold'}}>主机列表&nbsp;&nbsp;</span>
                  <a href="#">启用</a>&nbsp;&nbsp;
                  <a href="#">禁用</a>&nbsp;&nbsp;
                  <a href="#">删除</a>&nbsp;&nbsp;
                </Col>
                <Col span={8} style={{textAlign: 'right'}}>
                  <Input.Search
                    placeholder="input search text"
                    style={{width: 200}}
                    onSearch={value => console.log(value)}
                  />
                </Col>
              </Row>
            </div>
            <Row>
              <Col> <Table rowSelection={rowSelection} columns={columns} dataSource={data}/></Col>
            </Row>

          </Content>
        </Layout>
      </div>
    );
  }
}

DeviceMgr.propTypes = {};

export default DeviceMgr;
