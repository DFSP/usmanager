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

import pt.unl.fct.microservicemanagement.mastermanager.manager.hosts.HostFieldAvg;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HostMonitoringRepository extends JpaRepository<HostMonitoringEntity, Long> {

  @Query("select COUNT(1) "
      + "from HostMonitoringEntity monitoring "
      + "where monitoring.hostname = :hostname and monitoring.field = :field")
  long getCountHostField(@Param("hostname") String hostname, @Param("field") String field);

  @Query("select monitoring "
      + "from HostMonitoringEntity monitoring "
      + "where monitoring.hostname = :hostname and monitoring.field = :field")
  List<HostMonitoringEntity> getMonitoringHostLogByHostAndField(@Param("hostname") String hostname,
                                                                @Param("field") String field);

  @Query("select monitoring "
      + "from HostMonitoringEntity monitoring "
      + "where monitoring.hostname = :hostname")
  List<HostMonitoringEntity> getMonitoringHostLogByHost(@Param("hostname") String hostname);

  //TODO fix path

  @Query("select new pt.unl.fct.microservicemanagement.mastermanager.manager.hosts.HostFieldAvg(monitoring.hostname, "
      + "monitoring.field, monitoring.sumValue / monitoring.count, monitoring.count) "
      + "from HostMonitoringEntity monitoring "
      + "where monitoring.hostname = :hostname and monitoring.field = :field")
  HostFieldAvg getAvgHostField(@Param("hostname") String hostname, @Param("field") String field);

  //TODO fix path
  @Query("select new pt.unl.fct.microservicemanagement.mastermanager.manager.hosts.HostFieldAvg(monitoring.hostname, "
      + "monitoring.field, monitoring.sumValue / monitoring.count, monitoring.count) "
      + "from HostMonitoringEntity monitoring "
      + "where monitoring.hostname = :hostname")
  List<HostFieldAvg> getAvgHostFields(@Param("hostname") String serviceName);

}
