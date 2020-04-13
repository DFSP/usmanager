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
 * furnished to do so, subject to the following edgeHosts:
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

package pt.unl.fct.microservicemanagement.mastermanager.manager.host.edge;

import pt.unl.fct.microservicemanagement.mastermanager.exceptions.EntityNotFoundException;
import pt.unl.fct.microservicemanagement.mastermanager.manager.apps.AppEntity;
import pt.unl.fct.microservicemanagement.mastermanager.manager.ruleSystem.rules.hosts.HostRuleEntity;
import pt.unl.fct.microservicemanagement.mastermanager.manager.ruleSystem.rules.hosts.HostRulesService;
import pt.unl.fct.microservicemanagement.mastermanager.util.ObjectUtils;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EdgeHostsService {

  private final EdgeHostRepository edgeHosts;
  private final HostRulesService hostRulesService;

  public EdgeHostsService(EdgeHostRepository edgeHosts, HostRulesService hostRulesService) {
    this.edgeHosts = edgeHosts;
    this.hostRulesService = hostRulesService;
  }

  public Iterable<EdgeHostEntity> getEdgeHosts() {
    return edgeHosts.findAll();
  }

  public EdgeHostEntity getEdgeHost(String hostname) {
    return edgeHosts.findByHostname(hostname).orElseThrow(() ->
        new EntityNotFoundException(EdgeHostEntity.class, "hostname", hostname));
  }

  public EdgeHostEntity addEdgeHost(EdgeHostEntity edgeHost) {
    log.debug("Saving edgeHost {}", ToStringBuilder.reflectionToString(edgeHost));
    return edgeHosts.save(edgeHost);
  }

  public EdgeHostEntity updateEdgeHost(String hostname, EdgeHostEntity newEdgeHost) {
    EdgeHostEntity edgeHost = getEdgeHost(hostname);
    ObjectUtils.copyValidProperties(newEdgeHost, edgeHost);
    return edgeHosts.save(edgeHost);
  }

  public void deleteEdgeHost(String hostname) {
    var edgeHost = getEdgeHost(hostname);
    edgeHosts.delete(edgeHost);
  }

  public List<EdgeHostEntity> getHostsByPartialHostname(String partialHostname) {
    return edgeHosts.findByHostnameContaining(partialHostname);
  }

  public List<EdgeHostEntity> getHostsByRegion(String region) {
    return edgeHosts.findByRegion(region);
  }

  public List<EdgeHostEntity> getHostsByCountry(String country) {
    return edgeHosts.findByCountry(country);
  }

  public boolean hasEdgeHost(String hostname) {
    return edgeHosts.hasEdgeHost(hostname);
  }

  public List<HostRuleEntity> getRules(String hostname) {
    assertHostExists(hostname);
    return edgeHosts.getRules(hostname);
  }

  public void addRule(String hostname, String ruleName) {
    EdgeHostEntity edgeHost = getEdgeHost(hostname);
    HostRuleEntity rule = hostRulesService.getRule(ruleName);
    log.info("Adding rule {} to edge host {}", ruleName, hostname);
    edgeHost = edgeHost.toBuilder().hostRule(rule).build();
    edgeHosts.save(edgeHost);
  }

  public void addRules(String hostname, List<String> ruleNames) {
    ruleNames.forEach(ruleName -> addRule(hostname, ruleName));
  }

  public void removeRule(String hostname, String rule) {
    removeRules(hostname, List.of(rule));
  }

  public void removeRules(String hostname, List<String> rules) {
    var host = getEdgeHost(hostname);
    log.info("Removing rules {}", rules);
    host.getHostRules()
        .removeIf(rule -> rules.contains(rule.getName()));
    edgeHosts.save(host);
  }

  private void assertHostExists(String hostname) {
    if (!edgeHosts.hasEdgeHost(hostname)) {
      throw new EntityNotFoundException(AppEntity.class, "hostname", hostname);
    }
  }

}
