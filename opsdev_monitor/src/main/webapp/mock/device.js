'use strict';

let Mock = require('mockjs');


const devices = [];
for (let i = 0; i < 3; i++) {
  devices.push({
    id: Mock.mock("@integer"),
    title: Mock.mock("@string")
  });
}

const groups = [];
for (let i = 0; i < 5; i++) {
  groups.push({
    id: Mock.mock("@integer"),
    title: Mock.mock("@string"),
    children: devices
  });
}

module.exports = {

  'GET /api/device': function (req, res) {
    console.log(req.body)
    setTimeout(function () {
      res.json({
        success: true,
        data: {
          groups: groups,
          devices: devices
        }
      });
    }, 500);
  },

};
