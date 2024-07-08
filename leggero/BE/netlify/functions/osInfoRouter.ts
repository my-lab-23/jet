import { authenticateAPIKey } from "./auth"

//

const express = require('express');
const router = express.Router();
const os = require('os');
const { exec } = require('child_process');

//

router.get('/os-info', authenticateAPIKey, (req, res) => {
  let osInfo = {
    platform: os.platform(),
    cpu_architecture: os.arch(),
    total_memory: os.totalmem(),
    free_memory: os.freemem(),
    uptime: os.uptime()
  };

  if (os.platform() === 'linux') {
    exec('cat /etc/*-release', (error, stdout) => {
      if (error) {
        osInfo.distro_info = 'Error retrieving OS distribution information';
      } else {
        osInfo.distro_info = stdout;
      }
      res.send(osInfo);
    });
  } else {
    res.send(osInfo);
  }
});

//

module.exports = router;
