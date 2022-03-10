import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ServisTemelUpdatePage from './servis-temel-update.page-object';

const expect = chai.expect;
export class ServisTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.servisTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-servisTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ServisTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('servis-temel-heading'));
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
    await navBarPage.getEntityPage('servis-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateServisTemel() {
    await this.createButton.click();
    return new ServisTemelUpdatePage();
  }

  async deleteServisTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const servisTemelDeleteDialog = new ServisTemelDeleteDialog();
    await waitUntilDisplayed(servisTemelDeleteDialog.deleteModal);
    expect(await servisTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.servisTemel.delete.question/);
    await servisTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(servisTemelDeleteDialog.deleteModal);

    expect(await isVisible(servisTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
