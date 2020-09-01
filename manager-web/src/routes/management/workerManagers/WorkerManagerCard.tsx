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

import React from "react";
import Card from "../../../components/cards/Card";
import {IWorkerManager} from "./WorkerManager";
import CardItem from "../../../components/list/CardItem";

interface WorkerManagerCardProps {
  workerManager: IWorkerManager;
}

type Props = WorkerManagerCardProps;

const CardWorkerManager = Card<IWorkerManager>();
const WorkerManagerCard = ({workerManager}: Props) => (
  <CardWorkerManager title={workerManager.id.toString()}
                    link={{to: {pathname: `/worker-managers/${workerManager.id}`, state: workerManager}}}
                    height={'175px'}
                    margin={'10px 0'}
                    hoverable>
    <CardItem key={'startedAt'}
              label={'Started at'}
              value={workerManager.startedAt}/>
    <CardItem key={'container'}
              label={'Container'}
              value={workerManager.container.containerId}/>
    <CardItem key={'host'}
              label={'Host'}
              value={workerManager.container.hostname}/>
  </CardWorkerManager>
);

export default WorkerManagerCard;