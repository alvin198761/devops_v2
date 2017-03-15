'use strict';

let Mock = require('mockjs');

module.exports = {

  'GET /api/chart': function (req, res) {
    let time = new Date().getTime();
    let items = [];
    for (let i = 0; i < 10; i++) {
      time += 1000 * 60;
      items.push({
        time: time,
        value: Mock.Random.integer(10, 100),
        value1: Mock.Random.integer(20, 50),
        value2: Mock.Random.integer(40, 80),
      })
    }
    // let data = Mock.mock({
    //   "items|10": [
    //     {
    //       "time": '@datetime("yyyy-MM-dd HH:mm:ss")',
    //       "value": '@integer'
    //     }
    //   ]
    // })
    setTimeout(function () {
      res.json(items);
    }, 1000);
  },

};
