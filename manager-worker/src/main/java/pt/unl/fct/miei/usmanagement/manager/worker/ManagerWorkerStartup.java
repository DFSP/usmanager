/*
 * MIT License
 *
 * Copyright (c) 2020 manager
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

package pt.unl.fct.miei.usmanagement.manager.worker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pt.unl.fct.miei.usmanagement.manager.database.hosts.HostAddress;
import pt.unl.fct.miei.usmanagement.manager.worker.exceptions.WorkerManagerException;
import pt.unl.fct.miei.usmanagement.manager.worker.management.hosts.HostsService;
import pt.unl.fct.miei.usmanagement.manager.worker.management.monitoring.HostsMonitoringService;
import pt.unl.fct.miei.usmanagement.manager.worker.management.monitoring.ServicesMonitoringService;
import pt.unl.fct.miei.usmanagement.manager.worker.symmetricds.SymService;

@Slf4j
@Component
public class ManagerWorkerStartup implements ApplicationListener<ApplicationReadyEvent> {

  private final SymService symService;
  private final HostsService hostsService;
  private final ServicesMonitoringService servicesMonitoringService;
  private final HostsMonitoringService hostsMonitoringService;

  public ManagerWorkerStartup(SymService symService, HostsService hostsService,
                              ServicesMonitoringService servicesMonitoringService,
                              HostsMonitoringService hostsMonitoringService) {
    this.symService = symService;
    this.hostsService = hostsService;
    this.servicesMonitoringService = servicesMonitoringService;
    this.hostsMonitoringService = hostsMonitoringService;
  }

  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    // 1. start zookeeper process
    // 2. start kafka process
    hostsService.setHostAddress();
    symService.startSymmetricDSServer();
   /* try {
      hostsService.clusterHosts();
    } catch (WorkerManagerException e) {
      log.error(e.getMessage());
    }
    servicesMonitoringService.initServiceMonitorTimer();
    hostsMonitoringService.initHostMonitorTimer();*/
  }

}
