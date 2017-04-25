/**
 * Created by tangzhichao on 2017/4/17.
 */
import React from 'react';
import {Tabs,Button} from 'antd';

const TabPane = Tabs.TabPane;

const MainConsole = (props) => {
  return (
    <Tabs style={ {minHeight: 500}}
      hideAdd
      type="editable-card"
      tabBarExtraContent={<Button>自定义按钮</Button>}>
      <TabPane tab="主控制台" key="1">主控制台</TabPane>
      <TabPane tab="常用实例1" key="2">常用实例1</TabPane>
      <TabPane tab="常用实例2" key="3">常用实例2</TabPane>
    </Tabs>
  );
};

MainConsole.propTypes = {
};

export default MainConsole;
