import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import TabloTemelUpdatePage from './tablo-temel-update.page-object';

const expect = chai.expect;
export class TabloTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.tabloTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-tabloTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class TabloTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('tablo-temel-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('tablo-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateTabloTemel() {
    await this.createButton.click();
    return new TabloTemelUpdatePage();
  }

  async deleteTabloTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const tabloTemelDeleteDialog = new TabloTemelDeleteDialog();
    await waitUntilDisplayed(tabloTemelDeleteDialog.deleteModal);
    expect(await tabloTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.tabloTemel.delete.question/);
    await tabloTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(tabloTemelDeleteDialog.deleteModal);

    expect(await isVisible(tabloTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
