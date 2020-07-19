/*
 * MIT License
 *
 * Copyright (c) 2020 usmanager
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pt.unl.fct.microservicemanagement.mastermanager;

import pt.unl.fct.microservicemanagement.mastermanager.exceptions.MasterManagerException;
import pt.unl.fct.microservicemanagement.mastermanager.manager.hosts.HostsService;
import pt.unl.fct.microservicemanagement.mastermanager.manager.monitoring.ContainersMonitoringService;
import pt.unl.fct.microservicemanagement.mastermanager.manager.monitoring.HostsMonitoringService;
import pt.unl.fct.microservicemanagement.mastermanager.manager.monitoring.MasterManagerMonitoringService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MasterManagerStartup implements ApplicationListener<ApplicationReadyEvent> {

  private final HostsService hostsService;
  private final ContainersMonitoringService containersMonitoringService;
  private final HostsMonitoringService hostsMonitoringService;
  private final MasterManagerMonitoringService masterManagerMonitoringService;

  public MasterManagerStartup(@Lazy HostsService hostsService,
                              @Lazy ContainersMonitoringService containersMonitoringService,
                              @Lazy HostsMonitoringService hostsMonitoringService,
                              @Lazy MasterManagerMonitoringService masterManagerMonitoringService) {
    this.hostsService = hostsService;
    this.containersMonitoringService = containersMonitoringService;
    this.hostsMonitoringService = hostsMonitoringService;
    this.masterManagerMonitoringService = masterManagerMonitoringService;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    hostsService.setMachineInfo();
    try {
      hostsService.clusterHosts();
    } catch (MasterManagerException e) {
      log.error(e.getMessage());
    }
    containersMonitoringService.initContainerMonitorTimer();
    hostsMonitoringService.initHostMonitorTimer();
    masterManagerMonitoringService.initMasterManagerMonitorTimer();
  }

}
