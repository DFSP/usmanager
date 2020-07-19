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

package pt.unl.fct.microservicemanagement.mastermanager.manager.monitoring;

import pt.unl.fct.microservicemanagement.mastermanager.manager.monitoring.event.HostEventEntity;
import pt.unl.fct.microservicemanagement.mastermanager.manager.monitoring.event.HostEventRepository;
import pt.unl.fct.microservicemanagement.mastermanager.manager.rulesystem.decision.DecisionEntity;
import pt.unl.fct.microservicemanagement.mastermanager.manager.rulesystem.decision.DecisionsService;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class HostsEventsService {

  private final HostEventRepository hostEvents;
  private final DecisionsService decisionsService;

  public HostsEventsService(HostEventRepository hostEvents, DecisionsService decisionsService) {
    this.hostEvents = hostEvents;
    this.decisionsService = decisionsService;
  }

  public List<HostEventEntity> getHostEventsByHostname(String hostname) {
    return hostEvents.findByHostname(hostname);
  }

  HostEventEntity saveHostEvent(String hostname, String decisionName) {
    DecisionEntity decision = decisionsService.getHostPossibleDecision(decisionName);
    HostEventEntity hostEvent = hostEvents
        .findByHostname(hostname).stream().findFirst()
        .orElse(HostEventEntity.builder().hostname(hostname).decision(decision).count(0).build());
    if (!Objects.equals(hostEvent.getDecision().getId(), decision.getId())) {
      hostEvent.setDecision(decision);
      hostEvent.setCount(1);
    } else {
      hostEvent.setCount(hostEvent.getCount() + 1);
    }
    return this.hostEvents.save(hostEvent);
  }

}
