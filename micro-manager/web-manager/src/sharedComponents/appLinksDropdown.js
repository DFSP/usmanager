/*
 * MIT License
 *
 * Copyright (c) 2020 micro-manager
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

import React from 'react';
import M from 'materialize-css';
import { Link } from 'react-router-dom';

export class AppLinksDropdown extends React.Component {
  constructor (props) {
    super(props);
    const navLinks = this.props.links;
    this.state = { links: navLinks };
  }

  componentDidMount () {
    const elems = document.querySelectorAll('.dropdown-trigger');
    M.Dropdown.init(elems, { coverTrigger: false });
  }

    renderNavLinks = () => {
      let links;
      links = this.state.links.map(function (link, index) {
        return (
          <li key={index}>
            <Link to={link.link}>
              {link.name}
            </Link>
          </li>
        );
      });
      return links;
    };

    render () {
      return (
        <div className='col s12 hide-on-med-and-up'>
          <button className='dropdown-trigger btn btn-small waves-effect waves-teal btn-flat col s12' href='#'
            data-target='linksDropdown'>
            <i className="material-icons right">menu</i>
                    Menu
          </button>
          <ul id='linksDropdown' className='dropdown-content'>
            {this.renderNavLinks()}
          </ul>
        </div>
      );
    }
}
